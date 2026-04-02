import java.util.Scanner

class SemanticVersion(
    var major: Int,
    var minor: Int,
    var patch: Int,
    var preRelease: String?,
    var metadata: String?,
) {
    companion object {
        val initial = SemanticVersion(0, 0, 0, "SNAPSHOT", null)

        fun from(str: String): SemanticVersion? {
            // https://semver.org/#is-there-a-suggested-regular-expression-regex-to-check-a-semver-string
            val regex =
                Regex("""^v?(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(?:-((?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*)(?:\.(?:0|[1-9]\d*|\d*[a-zA-Z-][0-9a-zA-Z-]*))*))?(?:\+([0-9a-zA-Z-]+(?:\.[0-9a-zA-Z-]+)*))?$""")
            val matchResult = regex.find(str) ?: return null
            val groups = matchResult.groupValues
            return SemanticVersion(
                groups[1].toInt(),
                groups[2].toInt(),
                groups[3].toInt(),
                groups.getOrNull(4).takeIf { it?.isNotEmpty() == true },
                groups.getOrNull(5).takeIf { it?.isNotEmpty() == true },
            )
        }
    }

    override fun toString(): String {
        var version = "$major.$minor.$patch"
        if (!preRelease.isNullOrEmpty()) {
            version += "-$preRelease"
        }
        if (!metadata.isNullOrEmpty()) {
            version += "+$metadata"
        }
        return version
    }
}

fun String.runCommand(workingDir: File = rootDir): String? {
    return try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()

        proc.waitFor(60, java.util.concurrent.TimeUnit.SECONDS)
        proc.inputStream.bufferedReader().readText().trim()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun createVersion(): String {
    "git fetch --tags".runCommand()
    val versionString = "git describe --tags --always --first-parent".runCommand() ?: ""
    val version = SemanticVersion.from(versionString) ?: SemanticVersion.initial
    if (version.preRelease != null) {
        val nextMajor = project.findProperty("nextVersion.major")?.toString()?.toIntOrNull()
        val nextMinor = project.findProperty("nextVersion.minor")?.toString()?.toIntOrNull()
        if (nextMajor != null && nextMajor > version.major) {
            version.major = nextMajor
            version.minor = 0
            version.patch = 0
        } else if (nextMinor != null && nextMinor > version.minor) {
            version.minor = nextMinor
            version.patch = 0
        } else {
            version.patch += 1
        }
        version.preRelease = "SNAPSHOT"
    }
    return version.toString()
}

val versionValue = createVersion()
extra.set("describedVersion", versionValue)

tasks.register("printVersion") {
    doLast {
        println(createVersion())
    }
}

tasks.register("createTagFromVersion") {
    doFirst {
        if ("git symbolic-ref --short HEAD".runCommand() != "main") {
            throw GradleException("This task must be run in main branch")
        }
        "git pull --rebase --tags".runCommand()
    }
    doLast {
        var version = createVersion().replace("-SNAPSHOT", "")
        if (!version.startsWith("v")) {
            version = "v$version"
        }
        val versionList = "git tag".runCommand()?.split("\n") ?: emptyList()
        if (versionList.contains(version)) {
            throw GradleException("`$version` already exists.")
        }

        var canContinue = false
        val scanner = Scanner(System.`in`)
        while (!canContinue) {
            println("[Confirm] Set tag for `$version`? [y/N] >")
            val input = scanner.nextLine()
            when (input.lowercase()) {
                "y" -> canContinue = true
                "n" -> throw GradleException("Cancelled.")
            }
        }

        val tagRet = ProcessBuilder("git", "tag", version, "main").inheritIO().start().waitFor()
        if (tagRet != 0) throw GradleException("invalid return code on `git tag`: $tagRet")

        val pushRet = ProcessBuilder("git", "push", "origin", version).inheritIO().start().waitFor()
        if (pushRet != 0) throw GradleException("invalid return code on `git push`: $pushRet")

        val releaseRet = ProcessBuilder("gh", "release", "create", version, "--generate-notes").inheritIO().start().waitFor()
        if (releaseRet != 0) throw GradleException("invalid return code on `gh release create`: $releaseRet")
    }
}

allprojects {
    beforeEvaluate {
        extra.set("describedVersion", createVersion())
    }
}
