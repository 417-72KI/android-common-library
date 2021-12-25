.SILENT:
.PHONY: tag

tag:
	./gradlew createTagFromVersion

release:
	$(MAKE) tag

test:
	./gradlew --continue ktlintCheck lintDebug testDebug --stacktrace
