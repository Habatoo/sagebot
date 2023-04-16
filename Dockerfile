FROM adoptopenjdk/openjdk11:alpine-slim

ARG dbUrl
ARG dbUserName
ARG dbPassword

ARG botName
ARG botToken
ARG chatPort
ARG chatHost

ENV dbUrl=$dbUrl
ENV dbUserName=$dbUserName
ENV dbPassword=$dbPassword

ENV botName=$botName
ENV botToken=$botToken
ENV chatPort=$chatPort
ENV chatHost=$chatHost

COPY . .

CMD ["./gradlew", "./gradlew bootRun"]
