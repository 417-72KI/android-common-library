.SILENT:
.PHONY: tag

tag: test
	./gradlew createTagFromVersion

release: tag

test:
	./gradlew --continue clean ktlintCheck lintDebug testDebug --stacktrace

lint: test

format:
	./gradlew --continue clean ktlintFormat
