#!/usr/bin/env bash

JAR_NAME="holi.jar"
CALENDAR_SOURCE_DIR="resources/calendars-source"
CALENDAR_OUTPUT_DIR="resources/calendars-generated"
BUILD_ROOT="target"

echo "Starting holiday generation"
rm -rf $CALENDAR_OUTPUT_DIR
mkdir -p $CALENDAR_OUTPUT_DIR
clojure -M:generate 80 $CALENDAR_SOURCE_DIR $CALENDAR_OUTPUT_DIR

echo Creating jar: "${BUILD_ROOT}/${JAR_NAME}"
clojure -T:build jar :build-root "$BUILD_ROOT" :jar-file "$JAR_NAME"
cp target/classes/META-INF/maven/io.github.luciolucio/holi/pom.xml "$BUILD_ROOT"
cp target/classes/META-INF/maven/io.github.luciolucio/holi/pom.properties "$BUILD_ROOT"

echo Installing...
mvn install:install-file -Dfile="${BUILD_ROOT}/${JAR_NAME}" -DpomFile="${BUILD_ROOT}/pom.xml"
