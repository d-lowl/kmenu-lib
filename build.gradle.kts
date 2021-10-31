
plugins {
    kotlin("multiplatform") version "1.5.31"
    id("maven-publish")
    kotlin("plugin.serialization") version "1.5.31"
}

group = "space.dlowl"
version = "1.1"

var gitlabName: String? = null
var gitlabValue: String? = null



repositories {
    mavenCentral()
}

//Get credentials
if (System.getenv()["CI_JOB_TOKEN"] != null) {
    gitlabName = "Job-Token"
    gitlabValue = System.getenv()["CI_JOB_TOKEN"]
} else {
    gitlabName = "Private-Token"
    val gitLabPrivateToken: String? by project
    gitlabValue = gitLabPrivateToken
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
                url = uri("https://gitlab.com/api/v4/projects/30308398/packages/maven")

                credentials(HttpHeaderCredentials::class) {
                    name = gitlabName
                    value = gitlabValue
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
            }
        }
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


