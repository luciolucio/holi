#!/usr/bin/env bash

echo "---------------------"
echo "New calenjars project"
echo "---------------------"
echo ""
echo "Enter a project name (e.g. company-calendars):"

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
curl -LO https://github.com/piposaude/calenjars/raw/master/resources/calenjars-template.zip

echo ""
echo "Decompressing template..."
unzip calenjars-template.zip
mv calenjars-template "$PROJECT_NAME"

sed -i '' "s/{{lib-name}}/$PROJECT_NAME/g" "$PROJECT_NAME/README.md" "$PROJECT_NAME/gen-lib.sh" "$PROJECT_NAME/resources/calendars.clj" "$PROJECT_NAME/resources/pom.xml"
sed -i '' "s/{{lib-ns}}/$NAMESPACE/g" "$PROJECT_NAME/resources/calendars.clj" "$PROJECT_NAME/resources/pom.xml"

FULL_PATH="$PROJECT_NAME/resources/src/${NAMESPACE//\.//}"
mkdir -p "$FULL_PATH"
mv "$PROJECT_NAME/resources/calendars.clj" "$FULL_PATH/$PROJECT_NAME.clj"

chmod a+x "$PROJECT_NAME/gen-lib.sh"
mkdir "$PROJECT_NAME/calendars"

echo ""
echo "Project successfully generated at $PROJECT_NAME/"
