@file:Suppress("PrivatePropertyName")

package com.proyecto404.socialnetwork.api.e2e

import com.eclipsesource.json.JsonObject
import com.proyecto404.socialnetwork.api.Api
import com.proyecto404.socialnetwork.api.ApiConfiguration
import com.proyecto404.socialnetwork.console.formatters.StoppedClock
import com.proyecto404.socialnetwork.core.application.SessionTokenGeneratorStub
import com.proyecto404.socialnetwork.core.domain.UserExamples.alice
import com.proyecto404.socialnetwork.core.domain.UserExamples.bob
import com.proyecto404.socialnetwork.core.domain.following.Followings
import com.proyecto404.socialnetwork.core.infrastructure.CoreConfiguration
import com.proyecto404.socialnetwork.core.infrastructure.UseCaseProvider
import com.proyecto404.socialnetwork.core.infrastructure.db.NullDbChecker
import com.proyecto404.socialnetwork.core.infrastructure.inMemory.InMemoryRepositoryFactory
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import io.restassured.response.ValidatableResponse
import io.restassured.specification.RequestSender
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDateTime

class ApiEndToEndTests {
    @Test
    fun login() {
        givenAliceExists()

        post("/login", ALICE_CREDENTIALS) Then {
            body("sessionToken", equalTo(ALICE_SESSION_TOKEN))
        }
    }

    @Test
    fun `create user`() {
        post("/users", ALICE_REGISTRATION_INFO)

        assertAliceExists()
    }

    @Test
    fun `get user posts`() {
        givenAliceExists()
        createPost("test")
        createPost("test2")

        When {
            get("$baseUrl/users/@alice/posts")
        } Then {
            isSuccessfulJson()
            body("posts[0].id", equalTo(1))
            body("posts[0].message", equalTo("test"))
            body("posts[0].createdAt", equalTo(NOW_ISO_8601))
            body("posts[1].id", equalTo(2))
            body("posts[1].message", equalTo("test2"))
            body("posts[1].createdAt", equalTo(NOW_ISO_8601))
        }
    }

    @Test
    fun `create user post`() {
        givenAliceExists()
        val body = JsonObject()
            .add("sessionToken", ALICE_SESSION_TOKEN)
            .add("message", "Hello World").toString()

        post("/users/@alice/posts", body) Then {
            body("postId", equalTo(1))
        }
    }

    @Test
    fun `create following`() {
        givenAliceExists()
        givenBobExists()
        val body = JsonObject()
            .add("sessionToken", ALICE_SESSION_TOKEN)
            .add("userToFollow", bob.userName).toString()

        post("/users/@alice/followings", body) Then {
            assertFollowExist()
        }
    }

    private fun createPost(message: String) {
        useCaseProvider.createPost().exec(message, ALICE_SESSION_TOKEN)
    }

    private fun givenAliceExists() {
        useCaseProvider.signUp().exec(alice.userName, alice.password)
        useCaseProvider.logIn().exec(alice.userName, alice.password)
    }

    private fun givenBobExists() {
        useCaseProvider.signUp().exec(bob.userName, bob.password)
    }

    private fun assertAliceExists() {
        assertDoesNotThrow { useCaseProvider.logIn().exec(alice.userName, alice.password) }
    }

    private fun assertFollowExist() {
        assertThat(repositories.create(Followings::class).findByUser(alice.id)).isNotNull
    }

    @BeforeEach
    fun setUp() {
        api.start()
    }

    @AfterEach
    fun tearDown() {
        api.stop()
    }

    private fun post(endpoint: String, body: String): ValidatableResponse {
        return Given {
            body(body)
        } When {
            post("$baseUrl$endpoint")
        } Then {
            isSuccessfulJson()
        }
    }

    private infix fun ValidatableResponse.Then(block: ValidatableResponse.() -> Unit) = this.apply(block)

    private fun ValidatableResponse.isSuccessfulJson() {
        statusCode(200)
        contentType("application/json")
    }

    private fun When(block: RequestSender.() -> Response): Response =
        RestAssured.`when`().run<RequestSender, Response>(block)

    private val ALICE_CREDENTIALS = JsonObject().add("username", alice.userName).add("password", alice.password).toString()
    private val ALICE_REGISTRATION_INFO = JsonObject().add("username", alice.userName).add("password", alice.password).toString()
    private val ALICE_SESSION_TOKEN = "asd2315"
    private val baseUrl = "http://localhost:8080"
    private val NOW = LocalDateTime.of(2021, 6, 9, 14, 52, 0)
    private val NOW_ISO_8601 = "2021-06-09T14:52:00Z"
    private val stoppedClock = StoppedClock.at(NOW)
    private val sessionTokenGenerator = SessionTokenGeneratorStub(ALICE_SESSION_TOKEN)
    private val repositories = InMemoryRepositoryFactory()
    private val useCaseProvider = UseCaseProvider(CoreConfiguration(sessionTokenGenerator, stoppedClock, repositories))
    private val api = Api(ApiConfiguration(useCaseProvider, NullDbChecker(), 8080))
}
