[![publish](https://github.com/417-72KI/android-common-library/actions/workflows/publish.yml/badge.svg?branch=main)](https://github.com/417-72KI/android-common-library/actions/workflows/publish.yml)
[![latest](https://img.shields.io/github/v/release/417-72KI/android-common-library?label=latest)](https://github.com/417-72KI/android-common-library/releases/latest)
[![license](https://img.shields.io/github/license/417-72KI/android-common-library)](https://github.com/417-72KI/android-common-library/blob/main/LICENSE)

# android-common-library
An android library to reduce frequently used functions/utilities in my apps.

## Requirement
### v5.x
- JDK 17+
- Gradle 8.1.0+
- Android Studio Giraffe 2022.3.1+

### v4.x
- JDK 11+
- Gradle 8.0.1+
- Android Studio Electric Eel 2022.1.1+

### v2.x
- JDK 11+
- Gradle 7.1.1+
- Android Studio Arctic Fox 2020.3.1+

### v1.0.x
- JDK 8+
- Gradle 4.2.2+

## Usage

### top-level `build.gradle`

```groovy
allprojects {
    repositories {
        ・・・
        maven {
            url 'https://maven.pkg.github.com/417-72KI/android-common-library'
            credentials {
                username = project.findProperty("gpr.user") ?: System.getenv("GITHUB_USER")
                password = project.findProperty("gpr.key") ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}
```

### `app/build.gradle`

```groovy
dependencies {
    implementation 'jp.room417:android-common-library:5.0.0'
    implementation 'jp.room417:twitter4android:5.0.0' // If use Twitter
}
```
