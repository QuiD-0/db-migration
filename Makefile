export

DOCKER_COMPOSE = cd infra && docker-compose

.PHONY: start
start: erase build up ## clean current environment, recreate dependencies and spin up again

.PHONY: erase
erase: ## stop and delete containers, clean volumes
		$(DOCKER_COMPOSE) stop
		$(DOCKER_COMPOSE) rm -v -f

.PHONY: build
build: ## build environment and initialize and project dependencies
		$(DOCKER_COMPOSE) build

.PHONY: up
up: ## spin up environment
		$(DOCKER_COMPOSE) up -d --force-recreate --remove-orphans

.PHONY: stop
stop: ## spin up environment
		$(DOCKER_COMPOSE) down