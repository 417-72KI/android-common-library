# android-common-library
An android library to reduce frequently used functions/utilities in my apps.

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
    implementation 'jp.room417:android-common-library:1.0.1'
    implementation 'jp.room417:twitter4android:1.0.1' // If use Twitter
}
```
