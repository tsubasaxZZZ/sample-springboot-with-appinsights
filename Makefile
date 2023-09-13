IMAGE_NAME ?= tsubasaxzzz/demo-spring-openjdkms
TAG ?= latest
AI_CONNECTION_STRING ?= NOT_SET

.PHONY: build # build the project by using maven, before move demo directory
build:
	cd demo && ./mvnw clean package -DskipTests=true

.PHONY: docker-build
docker-build:
	docker build -t $(IMAGE_NAME):$(TAG) .

.PHONY: docker-push
docker-push:
	docker push $(IMAGE_NAME):$(TAG)

.PHONY: build-k8sdeploy-yaml
build-k8sdeploy-yaml:
	@if [ "$(AI_CONNECTION_STRING)" = "NOT_SET" ]; then \
		echo "Error: AI_CONNECTION_STRING is not set"; \
		exit 1; \
	fi
	@sed -e 's|image: tsubasaxzzz/demo-spring-openjdkms:.*|image: $(IMAGE_NAME):$(TAG)|' \
	     -e '/value: InstrumentationKey=/c\          value: "$(AI_CONNECTION_STRING)"' deployment-template.yaml > deployment.yaml

.PHONY: build-push
build-push: build docker-build docker-push build-k8sdeploy-yaml
