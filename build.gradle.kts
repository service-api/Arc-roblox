plugins {
    kotlin("jvm") version "2.2.0"
    `maven-publish`
}

base {
    archivesName.set("roblox")
}

group = "arc.roblox"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.javadoc)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = "arc"
            artifactId = project.base.archivesName.get()
            version = project.version.toString()

            artifact(sourcesJar)
            artifact(javadocJar)
        }
    }

    repositories {
        mavenLocal()
    }
}