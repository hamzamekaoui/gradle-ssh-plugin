@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797
plugins {
    alias(libs.plugins.gradlePublishPlugin)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

version = "0.0.1"
group = "com.azmah.ssh"

dependencies {
    implementation(libs.sshdApacheMina)
    testImplementation(libs.assertj)
    testImplementation(libs.junitJupiter)
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
        val functionalTest by registering(JvmTestSuite::class)
    }
}

configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])

gradlePlugin {
    plugins {
        val sshGradlePlugin by creating {
            id = "com.azmah.ssh"
            implementationClass = "com.azmah.ssh.SSHPlugin"
            displayName = "Gradle SSH plugin"
            description = "Plugin to run commands against a remote host using SSH"
        }
    }
    testSourceSets(sourceSets.getByName("functionalTest"))
}

tasks.named<Task>("check") {
    dependsOn("functionalTest")
}
