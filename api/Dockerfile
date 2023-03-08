FROM gradle:7.0.2-jdk16 as builder

WORKDIR /app

ADD . .

RUN gradle shadowJar

FROM openjdk:16-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/social_network.jar social_network.jar

CMD java -jar social_network.jar
