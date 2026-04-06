FROM gradle:9.4.1-jdk-alpine as build

WORKDIR /home/gradle/src

COPY . .

RUN ./gradlew bootJar +x test


FROM eclipse-temurin:21-jre

WORKDIR  /app

COPY  --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar app.jar


ENTRYPOINT ["java", "-jar", "app.jar"]
