plugins {
    java
    `maven-publish`
}

val javadoc by tasks.existing(Javadoc::class)
val jar by tasks.existing

group = "io.papermc"
version = "1.0.8"

val mcVersion = "1.17.1-R0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
}

val compileAndTest: Configuration by configurations.creating
configurations.compileOnly {
    extendsFrom(compileAndTest)
}
configurations.testImplementation {
    extendsFrom(compileAndTest)
}

dependencies {
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    compileAndTest("io.papermc.paper:paper-api:$mcVersion")

    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.8.1")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    withSourcesJar()
    withJavadocJar()
    disableAutoTargetJvm()
}

javadoc {
    isFailOnError = false
}

tasks.compileJava {
    options.encoding = Charsets.UTF_8.name()
    options.release.set(8)
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

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.compileTestJava {
    options.release.set(17) // Use new APIs for tests
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])

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
