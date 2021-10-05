plugins {
    kotlin("multiplatform") version "1.5.10"
    id("maven-publish")
}

group = "space.dlowl"
version = "1.1-SNAPSHOT"

val spaceUsername: String by project
val spacePassword: String by project

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    val publicationsFromMainHost =
        listOf(jvm()).map { it.name } + "kotlinMultiplatform"

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "space.dlowl"
                artifactId = "kmenu"
                version = version
                matching { it.name in publicationsFromMainHost }.all {
                    val targetPublication = this@all
                    tasks.withType<AbstractPublishToMaven>()
                        .matching { it.publication == targetPublication }
                        .configureEach { onlyIf { findProperty("isMainHost") == "true" } }
                }
            }
        }
        repositories {
            maven {
                url = uri("https://maven.pkg.jetbrains.space/dlowl/p/kmenu/kmenu-maven")
                credentials {
                    username = spaceUsername
                    password = spacePassword
                }
            }
        }
    }
    
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}


