plugins {

    java
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
    id("org.sonarqube") version "5.1.0.4882"
}

group = "com.employee"
version = "0.0.1-SNAPSHOT"
description = "Microservices project, where employee management is concerned "

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-validation")
	// implementation("org.springframework.boot:spring-boot-starter-webmvc")
	implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.cloud:spring-cloud-starter-openfeign:5.0.1")
	implementation("org.modelmapper:modelmapper:3.2.6")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.1")
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.json:json:20250517")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  //testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	annotationProcessor("org.projectlombok:lombok")
  testImplementation("org.apache.httpcomponents.client5:httpclient5")
	//testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
	// testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
  //testImplementation("org.springframework.boot:spring-boot-starter-amqp-test")
  developmentOnly("org.springframework.boot:spring-boot-docker-compose")
  testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
      testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.mockito:mockito-core:5.11.0")


}

tasks.withType<Test> {
	useJUnitPlatform()

}

jacoco {
    toolVersion = "0.8.11"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)	
    }
}

sonar {
    properties {
        property ("sonar.projectKey", "employee-backend")
        property ("sonar.projectName", "Employee Backend")
    }
}
