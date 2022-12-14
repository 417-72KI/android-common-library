.SILENT:
.PHONY: tag

tag: test
	./gradlew createTagFromVersion

release: tag

test:
	./gradlew --continue ktlintCheck lintDebug testDebug --stacktrace
