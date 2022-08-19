.DEFAULT_GOAL := help

JAR_NAME="holi.jar"
CALENDAR_SOURCE_DIR="resources/calendars-source"
CALENDAR_OUTPUT_DIR="resources/calendars-generated"
BUILD_ROOT="target"
DEFAULT_BRACKET=80

.PHONY: clean test fmt-check fix gen-holidays jar install release
SRC_AND_TEST := src test


clean:         ## Clean up CP cache and generated files
clean-prepare: ## Clean up files changed via prepare.sh
test:          ## Run tests
fmt-check:     ## Check code formatting
fix:           ## Fix code formatting automatically
lint:          ## Run clj-kondo for linting
jar:           ## Build jar
install:       ## Build jar and install it to the local Maven cache
release:       ## Build jar and push it to Clojars
help:          ## Show this help
	@fgrep -h "##" ${MAKEFILE_LIST} | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

clean:
	@rm -rf .cpcache/
	@rm -rf ${BUILD_ROOT}
	@rm -rf ${CALENDAR_OUTPUT_DIR}

clean-prepare:
	@git checkout -- build.clj new-holi-project.sh resources/holi-template/resources/deps.edn CUSTOM.md resources/holi-template.zip

test:
	@bin/eftest

fmt-check:
	@clojure -M:cljfmt -- check ${SRC_AND_TEST}

fix:
	@clojure -M:cljfmt -- fix ${SRC_AND_TEST}

lint:
	@clojure -M:lint ${SRC_AND_TEST}

gen-holidays:
	@echo "Starting holiday generation"
	@rm -rf ${CALENDAR_OUTPUT_DIR}
	@mkdir -p ${CALENDAR_OUTPUT_DIR}
	@clojure -M:generate ${DEFAULT_BRACKET} ${CALENDAR_SOURCE_DIR} ${CALENDAR_OUTPUT_DIR}

jar: gen-holidays
	@echo Creating jar: "${BUILD_ROOT}/${JAR_NAME}"
	@clojure -T:build jar :build-root ${BUILD_ROOT} :jar-file ${JAR_NAME}
	@cp target/classes/META-INF/maven/io.github.luciolucio/holi/pom.xml ${BUILD_ROOT}
	@cp target/classes/META-INF/maven/io.github.luciolucio/holi/pom.properties ${BUILD_ROOT}

install: jar
	@mvn install:install-file -Dfile="${BUILD_ROOT}/${JAR_NAME}" -DpomFile="${BUILD_ROOT}/pom.xml"

release: jar
	@mvn deploy:deploy-file -Dfile="${BUILD_ROOT}/${JAR_NAME}" -DpomFile="${BUILD_ROOT}/pom.xml" -DrepositoryId=clojars -Durl=https://clojars.org/repo
