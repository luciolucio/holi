#!/usr/bin/env bash

echo "---------------------"
echo "New holi project"
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
curl -LO https://github.com/luciolucio/holi/raw/1.1.0/resources/holi-template.zip

echo ""
echo "Decompressing template..."
unzip holi-template.zip
mv holi-template "$PROJECT_NAME"

sed -i '' "s/{{lib-name}}/$PROJECT_NAME/g" "$PROJECT_NAME/README.md" "$PROJECT_NAME/gen-lib.sh" "$PROJECT_NAME/resources/calendars.cljc" "$PROJECT_NAME/resources/build.clj"
sed -i '' "s/{{lib-ns}}/$NAMESPACE/g" "$PROJECT_NAME/resources/calendars.cljc" "$PROJECT_NAME/resources/build.clj"

NAMESPACE_UNDERSCORED="${NAMESPACE//-/_}"
FULL_PATH="$PROJECT_NAME/resources/src/${NAMESPACE_UNDERSCORED//\.//}"
PROJECT_NAME_UNDERSCORED="${PROJECT_NAME//-/_}"
mkdir -p "$FULL_PATH"
mv "$PROJECT_NAME/resources/calendars.cljc" "$FULL_PATH/$PROJECT_NAME_UNDERSCORED.cljc"

chmod a+x "$PROJECT_NAME/gen-lib.sh"
mkdir "$PROJECT_NAME/calendars"

echo ""
echo "Project successfully generated at $PROJECT_NAME/"
