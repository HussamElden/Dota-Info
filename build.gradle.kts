buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Build.androidBuildTools)
        classpath(Build.kotlinGradlePlugin)
        classpath(Build.sqlDelightGradlePlugin)
        classpath(Build.ktlintGradlePlugin)
    }
}

subprojects {
    val makeKtlintNonBlocking = {
        // Keep ktlint checks visible, but do not fail the build on violations.

        tasks.configureEach {
            if (name.startsWith("ktlint") && name.endsWith("Check")) {
                javaClass.methods
                    .firstOrNull { it.name == "setIgnoreFailures" && it.parameterCount == 1 }
                    ?.invoke(this, true)
            }
        }
    }

    val enableKtlint = {
        if (!plugins.hasPlugin("org.jlleitschuh.gradle.ktlint")) {
            apply(plugin = "org.jlleitschuh.gradle.ktlint")
        }
        pluginManager.withPlugin("org.jlleitschuh.gradle.ktlint") {
            makeKtlintNonBlocking()
        }
    }

    plugins.withId("org.jetbrains.kotlin.jvm") { enableKtlint() }
    plugins.withId("org.jetbrains.kotlin.android") { enableKtlint() }
    plugins.withId("kotlin-android") { enableKtlint() }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}