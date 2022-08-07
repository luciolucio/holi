#!/usr/bin/env bash

LIB_NAME="holi"
CALENDAR_SOURCE_DIR="resources/calendars-source"
OUTPUT_DIR="resources/calendars-generated"

echo "Starting holiday generation"
rm -rf $OUTPUT_DIR
mkdir -p $OUTPUT_DIR
clojure -M:generate 80 $CALENDAR_SOURCE_DIR $OUTPUT_DIR

echo Creating uberjar: "${LIB_NAME}.jar"
clojure -M:depstar -m hf.depstar.jar "${LIB_NAME}.jar"

echo Updating pom with latest dependecies
clojure -Spom

echo Deploying to clojars
mvn deploy:deploy-file -Dfile="${LIB_NAME}.jar" -DpomFile=pom.xml -DrepositoryId=clojars -Durl=https://clojars.org/repo
