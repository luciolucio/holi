#!/usr/bin/env bash

LIB_NAME="{{lib-name}}"

rm -rf generated
mkdir output
cp -R resources/src/ output/src
cp resources/pom.xml output
cp resources/deps.edn output

cd output || exit
clojure -A:calendars 80 "../calendars/" "../output/"
clojure -A:depstar -m hf.depstar.jar "${LIB_NAME}.jar"
cd .. || exit
mv "output/$LIB_NAME.jar" .
rm -rf output
mkdir generated
mv "$LIB_NAME.jar" generated

printf "%s.jar generated successfully\n" $LIB_NAME
