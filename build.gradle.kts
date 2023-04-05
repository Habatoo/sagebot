import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
//import nu.studer.gradle.jooq.JooqEdition
//import nu.studer.gradle.jooq.JooqGenerate

val postgresVersion = "42.6.0"
val telegramBotVersion = "6.5.0"

plugins {
	//id("nu.studer.jooq") version("6.0.1")
	//id("org.flywaydb.flyway") version("7.7.0")
	id("org.springframework.boot") version "2.7.9"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.sage"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

//tasks.clean {
//	delete("src/main/java")
//}

extra["springCloudVersion"] = "2020.0.4"

//val flywayMigration = configurations.create("flywayMigration")
//
//
//flyway {
//	validateOnMigrate = false
//	configurations = arrayOf("flywayMigration")
//	url = "jdbc:postgresql://localhost:5432/sagebase"
//	user = "sagebase"
//	password = "sagebase"
//}

dependencies {
//	flywayMigration("org.postgresql:postgresql:$postgresVersion")
//	jooqGenerator("org.postgresql:postgresql:$postgresVersion")

	implementation("org.flywaydb:flyway-core:7.5.1")
	runtimeOnly("org.postgresql:postgresql")

	implementation("org.springframework.boot:spring-boot-starter")
//	implementation("org.springframework.boot:spring-boot-starter-jooq")
	implementation("org.springframework.boot:spring-boot-starter-freemarker")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")

	implementation("org.telegram:telegrambots:$telegramBotVersion")
	implementation("org.telegram:telegrambots-spring-boot-starter:$telegramBotVersion")
	implementation("org.telegram:telegrambotsextensions:$telegramBotVersion")

	implementation("com.vdurmont:emoji-java:5.1.1")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	compileOnly("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("io.kotest:kotest-runner-junit5-jvm:5.5.4")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
	testImplementation("io.mockk:mockk:1.13.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

//jooq {
//	edition.set(JooqEdition.OSS)
//
//	configurations {
//		create("main") {
//			jooqConfiguration.apply {
//				jdbc.apply {
//					driver = "org.postgresql.Driver"
//					url = flyway.url
//					user = flyway.user
//					password = flyway.password
//				}
//				generator.apply {
//					name = "org.jooq.codegen.DefaultGenerator"
//					generate.apply {
//						isDeprecated = false
//						isRecords = true
//						isImmutablePojos = false
//						isFluentSetters = false
//						isJavaBeansGettersAndSetters = false
//						isSerializablePojos = true
//						isVarargSetters = false
//						isPojos = true
//						isNonnullAnnotation = true
//						isUdts = false
//						isRoutines = false
//						isIndexes = false
//						isRelations = true
//						isPojosEqualsAndHashCode = true
//					}
//					database.apply {
//						name = "org.jooq.meta.postgres.PostgresDatabase"
//						inputSchema = "public"
//						excludes = "flyway_schema_history|spatial_ref_sys|st_.*|_st.*"
//					}
//					target.apply {
//						packageName = "com.sage.bot.domain"
//					}
//					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
//				}
//			}
//		}
//	}
//
//	tasks.named<JooqGenerate>("generateJooq").configure {
//		inputs.files(fileTree("src/main/resources/db/migration"))
//			.withPropertyName("migrations")
//			.withPathSensitivity(PathSensitivity.RELATIVE)
//		allInputsDeclared.set(true)
//		outputs.upToDateWhen { false }
//	}
//}

