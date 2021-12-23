.SILENT:
.PHONY: tag

tag:
	./gradlew createTagFromVersion

test:
	./gradlew --continue ktlintCheck lintDebug testDebug --stacktrace
