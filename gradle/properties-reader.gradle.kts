import java.util.Properties

/**
 * Load all local properties defined on local.properties files in all projects (root and sub projects).
 */

val localPropertiesFilename = "local.properties"

fun getLocalProperties(dir: File): Properties? {
    val file = File(dir, localPropertiesFilename)
    if (!file.exists()) return null
    return Properties().apply {
        file.inputStream().use { load(it) }
    }
}

val rootLocalProperties = getLocalProperties(rootDir) ?: Properties()
extra.set("localProperties", rootLocalProperties)

allprojects {
    beforeEvaluate {
        getLocalProperties(projectDir)?.let { props ->
            (rootProject.extra.get("localProperties") as Properties).putAll(props)
        }
        extra.set("localProperties", rootProject.extra.get("localProperties"))
    }
}
