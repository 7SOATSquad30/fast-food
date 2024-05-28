.PHONY: test build run debug lint/fix infrastructure/up

infrastructure/up:
	docker-compose up -d database

down:
	docker-compose down --remove-orphans database

test:
	./gradlew test

build:
	./gradlew build

run:
	docker-compose up api

debug:
	./gradlew bootRun --debug-jvm

lint/fix:
	./gradlew spotlessApply