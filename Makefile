.PHONY: test build run debug lint/fix

test:
	./gradlew test

build:
	./gradlew build

run:
	./gradlew bootRun

debug:
	./gradlew bootRun --debug-jvm

lint/fix:
	./gradlew spotlessApply