.DEFAULT_GOAL := help

JAR_NAME="holi.jar"
CALENDAR_SOURCE_DIR="resources/calendars-source"
CALENDAR_OUTPUT_DIR="resources/calendars-generated"
BUILD_ROOT="target"
# WARNING! Do not increase this bracket beyond 75! This keeps the size of `WEEKEND*.datelist` < 65k to allow slurping when uberjarring
DEFAULT_BRACKET=75
CLJ_TEST_LIB_HOME="test-lib/clj"
CLJ_TEST_LIB_TARGET="${CLJ_TEST_LIB_HOME}/target"
CLJS_TEST_LIB_HOME="test-lib/cljs"
CLJS_TEST_LIB_TARGET="${CLJS_TEST_LIB_HOME}/target"

.PHONY: clean test yarn-install test-cljs repl-cljs perftest watch fmt-check fix gen-holidays jar install release showcase
SRC_AND_TEST := src test


clean:         ## Clean up CP cache and generated files
clean-prepare: ## Clean up files changed via prepare.sh
test:          ## Run tests
test-cljs:     ## Run cljs tests
repl-cljs:     ## Start a ClojureScript REPL
perftest:      ## Run performance tests
watch:         ## Run tests and start watch
fmt-check:     ## Check code formatting
fix:           ## Fix code formatting automatically
lint:          ## Run clj-kondo for linting
jar:           ## Build jar
test-all-libs: ## Run clj and cljs tests against generated jar
install:       ## Build jar and install it to the local Maven cache
prepare:       ## Prepare the release by updating the version number in the right places
tag:           ## Tag a version (Note: this will trigger a release)
release:       ## Build jar and push it to Clojars
showcase:      ## Build showcase and save to publishing dir
help:          ## Show this help
	@fgrep -h "##" ${MAKEFILE_LIST} | fgrep -v fgrep | sed -e 's/\\$$//' | sed -e 's/##//'

clean:
	@rm -rf .cpcache/
	@rm -rf .shadow-cljs/
	@rm -rf out/
	@rm -rf ${BUILD_ROOT}
	@rm -rf ${CALENDAR_OUTPUT_DIR}
	@rm -rf ${CLJ_TEST_LIB_TARGET}
	@rm -rf "${CLJ_TEST_LIB_HOME}/.cpcache"
	@rm -rf ${CLJS_TEST_LIB_TARGET}
	@rm -rf "${CLJS_TEST_LIB_HOME}/.cpcache"
	@rm -rf "${CLJS_TEST_LIB_HOME}/.shadow-cljs"
	@rm -rf "${CLJS_TEST_LIB_HOME}/out"

clean-prepare:
	@git checkout -- build.clj new-holi-project.sh resources/holi-template/resources/deps.edn doc/04-CUSTOM.md resources/holi-template.zip README.md doc/01-README.md showcase/src/luciolucio/holi/showcase/pages/home/core.cljs showcase/deps.edn docs

test: gen-holidays # Holidays are needed so that `luciolucio.holi.core/holiday-datelists` can load, but are not used in tests
	@bin/kaocha :unit

yarn-install:
	@yarn install

test-cljs: yarn-install gen-holidays # Holidays are needed so that `luciolucio.holi.core/holiday-datelists` can load, but are not used in tests
	@clojure -M:test:shadow-cljs compile test
	@node out/tests.js

repl-cljs: yarn-install gen-holidays
	@clojure -M:test:shadow-cljs watch repl

perftest: gen-holidays
	@bin/kaocha :performance --no-capture-output

watch:
	@bin/kaocha :unit --watch --fail-fast --no-randomize

fmt-check:
	@clojure -M:cljfmt -- check ${SRC_AND_TEST}
	@cd showcase && bin/fmt

fix:
	@clojure -M:cljfmt -- fix ${SRC_AND_TEST}
	@cd showcase && bin/fmt fix

lint:
	@clojure -M:lint ${SRC_AND_TEST}

gen-holidays:
	@echo "Starting holiday generation"
	@rm -rf ${CALENDAR_OUTPUT_DIR}
	@mkdir -p ${CALENDAR_OUTPUT_DIR}
	@clojure -M:generate ${DEFAULT_BRACKET} ${CALENDAR_SOURCE_DIR} ${CALENDAR_OUTPUT_DIR}

jar: gen-holidays
	@echo "Doing the jar"
	@echo Creating jar: "${BUILD_ROOT}/${JAR_NAME}"
	@clojure -T:build jar :build-root ${BUILD_ROOT} :jar-file ${JAR_NAME}
	@cp target/classes/META-INF/maven/io.github.luciolucio/holi/pom.xml ${BUILD_ROOT}
	@cp target/classes/META-INF/maven/io.github.luciolucio/holi/pom.properties ${BUILD_ROOT}

test-lib-clj: jar
	@echo "Testing CLJ lib"
	@mkdir -p "${CLJ_TEST_LIB_TARGET}"
	@cp "${BUILD_ROOT}/${JAR_NAME}" "${CLJ_TEST_LIB_TARGET}"
	@bin/test-lib-clj.sh

test-lib-cljs: jar
	@echo "Testing CLJS lib"
	@mkdir -p "${CLJS_TEST_LIB_TARGET}"
	@cp "${BUILD_ROOT}/${JAR_NAME}" "${CLJS_TEST_LIB_TARGET}"
	@bin/test-lib-cljs.sh

test-all-libs: test-lib-clj test-lib-cljs

install: jar
	@mvn install:install-file -Dfile="${BUILD_ROOT}/${JAR_NAME}" -DpomFile="${BUILD_ROOT}/pom.xml"

prepare:
	@bin/prepare.sh ${VERSION_NUMBER}

tag:
	@bin/tag.sh

release: jar
	@mvn deploy:deploy-file -Dfile="${BUILD_ROOT}/${JAR_NAME}" -DpomFile="${BUILD_ROOT}/pom.xml" -DrepositoryId=clojars -Durl=https://clojars.org/repo

showcase: install # Must install for showcase to build with the new version even prior to it being published to clojars
	@cd showcase && bin/build
	@rm -rf docs && mkdir docs
	@mv showcase/build/** docs
