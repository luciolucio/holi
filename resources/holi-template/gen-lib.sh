#!/usr/bin/env bash

LIB_NAME="{{lib-name}}"

rm -rf generated
mkdir output
cp -R resources/src/ output/src
cp resources/build.clj output
cp resources/deps.edn output

cd output || exit
mkdir -p calendars-generated
clojure -M:calendars 80 "../calendars/" "calendars-generated"
clojure -T:build jar :build-root . :jar-file "${LIB_NAME}.jar"
cd .. || exit
mv "output/$LIB_NAME.jar" .
rm -rf output
mkdir generated
mv "$LIB_NAME.jar" generated

printf "%s.jar generated successfully under generated/\n" $LIB_NAME
