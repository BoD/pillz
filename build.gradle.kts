import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.benManes)

    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
//        val reject = setOf("alpha", "beta", "rc")
        val reject = setOf<String>()
        reject.any { candidate.version.contains(it, ignoreCase = true) }
    }
}

// `./gradlew dependencyUpdates` to see new dependency versions
