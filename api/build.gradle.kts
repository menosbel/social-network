import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import io.github.cdimascio.dotenv.dotenv
import nu.studer.gradle.jooq.JooqGenerate
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    id("nu.studer.jooq") version "6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("org.flywaydb.flyway") version "7.11.2"
}

buildscript {
    dependencies {
        classpath("io.github.cdimascio:dotenv-kotlin:6.2.2")
    }
}

val dotenv = dotenv {
    directory = rootProject.projectDir.absolutePath
    ignoreIfMissing = true
}

group = "com.proyecto404.socialnetwork"
version = "1.0"

project.ext {
    set("GENERATE_JOOQ", dotenv["GENERATE_JOOQ"] == "1")
}

repositories { mavenCentral() }

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.javalin:javalin:3.13.11")
    implementation("org.slf4j:slf4j-simple:1.7.30")
    implementation("com.eclipsesource.minimal-json:minimal-json:0.9.5")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.jooq:jooq:3.14.13")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    implementation("org.postgresql:postgresql:42.2.23")
    jooqGenerator("org.postgresql:postgresql:42.2.23")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.assertj:assertj-core:3.19.0")
    testImplementation("net.bytebuddy:byte-buddy:1.11.1") // Added for mockk compatibility with JDK16
    testImplementation("io.mockk:mockk:1.12.0")
    testImplementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation("io.rest-assured:kotlin-extensions:4.4.0")
}

tasks.withType<Test> { useJUnitPlatform() }

tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = "16" }

tasks.getByName<ShadowJar>("shadowJar") {
    archiveFileName.set("social_network.jar")
    manifest {
        attributes(mapOf(
            "Main-Class" to "com.proyecto404.socialnetwork.api.ApiMainKt",
        ))
    }
}

flyway {
    url = dotenv["DB_URL"]
    user = dotenv["DB_USER"]
    password = dotenv["DB_PASSWORD"]
    schemas = arrayOf("public")
    locations = arrayOf("filesystem:${project.projectDir}/src/main/resources/db")
}

jooq {
    version.set("3.14.13")

    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(project.ext["GENERATE_JOOQ"] as Boolean)
            jooqConfiguration.apply {
                logging = org.jooq.meta.jaxb.Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = dotenv["DB_URL"]
                    user = dotenv["DB_USER"]
                    password = dotenv["DB_PASSWORD"]
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        includes = ".*"
                        excludes = ""
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "com.proyecto404.socialnetwork.infrastructure.db.jooq.generated"
                        directory = "src/main/jooq"
                    }
                }
            }
        }
    }
}

tasks.named<JooqGenerate>("generateJooq") {
    dependsOn("flywayMigrate")
    inputs.files(fileTree("src/main/resources/db"))
        .withPropertyName("migrations")
        .withPathSensitivity(PathSensitivity.RELATIVE)
    allInputsDeclared.set(true) // make jOOQ task participate in incremental builds and build caching
    outputs.cacheIf { true }
}
