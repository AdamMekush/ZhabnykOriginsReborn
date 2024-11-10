plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.papermc.paperweight.userdev") version "1.7.1" apply false
}

group = "com.starshootercity"
version = "2.3.18"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.json:json:20240303")
    implementation("net.objecthunter:exp4j:0.4.8")
    implementation(project(":core"))
    implementation(project(":version"))
    implementation(project(":1.20.4", "reobf"))
}

tasks {
    compileJava {
        options.release.set(17)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.test {
    useJUnitPlatform()
}