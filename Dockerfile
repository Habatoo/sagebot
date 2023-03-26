FROM adoptopenjdk/openjdk11:ubi
WORKDIR /sagebot

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .

COPY src src

CMD ["./gradlew", "./gradlew bootRun"]