plugins {
    java
    `maven-publish`
}

val javadoc by tasks.existing(Javadoc::class)
val jar by tasks.existing

group = "io.papermc"
version = "1.0.3-SNAPSHOT"

val mcVersion = "1.13.1-R0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    compileOnly("com.google.code.findbugs:jsr305:1.3.9")
    compileOnly("com.destroystokyo.paper:paper-api:$mcVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

javadoc {
    isFailOnError = false
}

val javadocJar by tasks.registering(Jar::class) {
    dependsOn(javadoc)
    classifier = "javadoc"
    from(javadoc)
}

// A couple aliases just to simplify task names
tasks.register("install") {
    group = "publishing"
    description = "Alias for publishToMavenLocal"
    dependsOn(tasks.named("publishToMavenLocal"))
}

tasks.register("deploy") {
    group = "publishing"
    description = "Alias for publish"
    dependsOn(tasks.named("publish"))
}

artifacts {
    add("archives", sourcesJar)
    add("archives", javadocJar)
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(javadocJar.get())

            pom {
                name.set("PaperLib")
                description.set("Plugin library for interfacing with Paper specific APIs with graceful fallback that maintains Spigot compatibility.")
                url.set("https://github.com/PaperMC/PaperLib")
                scm {
                    url.set("https://github.com/PaperMC/PaperLib")
                }
                issueManagement {
                    system.set("github")
                    url.set("https://github.com/PaperMC/PaperLib/issues")
                }
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
            }
        }
    }

    if (project.hasProperty("papermcRepoUser") && project.hasProperty("papermcRepoPass")) {
        val papermcRepoUser: String by project
        val papermcRepoPass: String by project

        val repoUrl = if (version.toString().endsWith("-SNAPSHOT")) {
            "https://papermc.io/repo/repository/maven-snapshots/"
        } else {
            "https://papermc.io/repo/repository/maven-releases/"
        }

        repositories {
            maven {
                url = uri(repoUrl)
                name = "Paper"
                credentials {
                    username = papermcRepoUser
                    password = papermcRepoPass
                }
            }
        }
    }
}
