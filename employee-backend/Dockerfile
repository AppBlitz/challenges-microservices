FROM gradle:9.3.0-jdk21 AS build

WORKDIR /home/gradle/src


COPY . .

RUN ./gradlew bootJar -x test


FROM eclipse-temurin:21-jre

WORKDIR  ./app

COPY  --from=build /home/gradle/src/build/libs/*-SNAPSHOT.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]







