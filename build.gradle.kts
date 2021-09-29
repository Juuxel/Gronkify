plugins {
    `java-gradle-plugin`
    scala
    `maven-publish`
    id("org.cadixdev.licenser") version "0.6.1"
}

group = "io.github.juuxel"
version = "1.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.6")
}

license {
    header(file("HEADER.txt"))
}

gradlePlugin {
    plugins.create("gronkify") {
        id = "io.github.juuxel.gronkify"
        implementationClass = "juuxel.gronkify.Gronkify"
    }
}

tasks {
    jar {
        from("COPYING.md")
    }
}

val env = System.getenv()
if ("MAVEN_URL" in env) {
    publishing {
        repositories {
            maven {
                url = uri(env.getValue("MAVEN_URL"))
                credentials {
                    username = env.getValue("MAVEN_USERNAME")
                    password = env.getValue("MAVEN_PASSWORD")
                }
            }
        }
    }
}
