#!/usr/bin/env bash

echo "---------------------"
echo "New calenjars project"
echo "---------------------"
echo ""
echo "Enter a project name (e.g. calendars):"

read -r PROJECT_NAME

if [ -z "$PROJECT_NAME" ]
then
      echo "Project name cannot be empty"
      echo ""
      exit 1
fi

echo "Enter your namespace (e.g. com.yourcompany):"

read -r NAMESPACE

if [ -z "$NAMESPACE" ]
then
      echo "Namespace cannot be empty"
      echo ""
      exit 1
fi

echo ""
echo "Downloading template..."
curl -LO https://github.com/luciolucio/calenjars/raw/master/resources/calenjars-template.zip

echo ""
echo "Decompressing template..."
unzip calenjars-template.zip
mv calenjars-template "$PROJECT_NAME"
