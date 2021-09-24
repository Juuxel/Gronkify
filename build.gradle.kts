plugins {
    `java-gradle-plugin`
    scala
    `maven-publish`
    id("org.cadixdev.licenser") version "0.6.1"
}

group = "io.github.juuxel"
version = "0.0.18"

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
