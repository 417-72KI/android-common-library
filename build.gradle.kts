buildscript {
    val minSDKVersion: Int by extra(23)
    val compileSDKVersion: Int by extra(36)
    val javaVersion: JavaVersion by extra(JavaVersion.VERSION_21)
    val jvmTarget: String by extra("21")

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle.plugin)
        // ktlint plugin classes for configure<...>
        classpath(libs.plugins.ktlint.get().let { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" })
    }
}

plugins {
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.kotlin.android) apply false
}

apply(from = "gradle/properties-reader.gradle.kts")
apply(from = "gradle/version.gradle.kts")

allprojects {
//    apply(plugin = libs.plugins.ktlint.get().pluginId)
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    repositories {
        google()
        mavenCentral()

        configurations.configureEach {
            // Don't cache changing modules at all.
            resolutionStrategy.cacheChangingModulesFor(0, "seconds")
        }
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.7.1")

        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
