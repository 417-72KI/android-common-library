import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
}

val min_sdk_version: Int by rootProject.extra
val target_sdk_version: Int by rootProject.extra
val compile_sdk_version: Int by rootProject.extra
val java_version: JavaVersion by rootProject.extra

android {
    namespace = "jp.room417.common"
    compileSdk = compile_sdk_version

    defaultConfig {
        minSdk = min_sdk_version
        targetSdk = target_sdk_version
        // versionCode 1
        // versionName project.extra["describedVersion"] as String

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = java_version
        targetCompatibility = java_version
    }
    testOptions {
        unitTests.all {
            useJUnitPlatform()
        }
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    // https://youtrack.jetbrains.com/issue/KT-33248
    // noinspection DifferentStdlibGradleVersion
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.register<Jar>("sourceJar") {
    from(android.sourceSets.getByName("main").java.srcDirs)
    archiveClassifier.set("sources")
}

afterEvaluate {
    val localProperties = project.extra["localProperties"] as Properties
    publishing {
        publications {
            create<MavenPublication>("bundleReleaseAar") {
                from(components["release"])

                groupId = "jp.room417"
                artifactId = "android-common-library"
                version = project.extra["describedVersion"] as String
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/417-72KI/android-common-library")
                credentials {
                    username = localProperties.getProperty("gpr.user") ?: project.findProperty("gpr.user") as? String ?: System.getenv("GITHUB_USER")
                    password = localProperties.getProperty("gpr.key") ?: project.findProperty("gpr.key") as? String ?: System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}
