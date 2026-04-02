import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
}

val minSDKVersion: Int by rootProject.extra
val compileSDKVersion: Int by rootProject.extra
val javaVersion: JavaVersion by rootProject.extra

android {
    namespace = "jp.room417.common"
    compileSdk = compileSDKVersion

    defaultConfig {
        minSdk = minSDKVersion
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
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
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

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
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
