#!/usr/bin/env zsh

VERSION_NUMBER=${1?"Usage: prepare.sh VERSION_NUMBER"}

CHANGELOG=$(awk -v ver="$VERSION_NUMBER" '/^[*] / { if (p) { exit }; if ($2 == ver) { p=1 } } p && NF' CHANGELOG.md)

if [ -z "$CHANGELOG" ]
then
      print "No CHANGELOG for version $VERSION_NUMBER"
      exit 1
fi

print "Updating build version..."
sed "s/(def version \".*\")/(def version \"$VERSION_NUMBER\")/g" build.clj > new-build.clj
mv new-build.clj build.clj

print "Updating new project script..."
sed "s|raw/.*/resources|raw/$VERSION_NUMBER/resources|g" new-holi-project.sh > new-new.clj
mv new-new.clj new-holi-project.sh
chmod a+x new-holi-project.sh

print "Updating template's deps.edn..."
sed "s|io.github.luciolucio/holi {:mvn/version \".*\"}}|io.github.luciolucio/holi {:mvn/version \"$VERSION_NUMBER\"}}|g" resources/holi-template/resources/deps.edn > new-deps.edn
mv new-deps.edn resources/holi-template/resources/deps.edn

print "Updating custom project instructions..."
sed "s|holi/.*/new|holi/$VERSION_NUMBER/new|g" doc/CUSTOM.md > new-custom.md
mv new-custom.md doc/CUSTOM.md

print "Updating readme..."
sed "s|holi/.*/api|holi/$VERSION_NUMBER/api|g" README.md > new-readme.md
mv new-readme.md README.md

print "Updating custom project template"
cd resources || exit
rm holi-template.zip
zip holi-template.zip holi-template/**/*
zip holi-template.zip holi-template/**/.*

print "Verifying updates..."
NAMES=$(git diff --name-only)

if (echo "$NAMES" | grep build.clj) \
   && (echo "$NAMES" | grep new-holi-project.sh) \
   && (echo "$NAMES" | grep resources/holi-template/resources/deps.edn) \
   && (echo "$NAMES" | grep doc/CUSTOM.md) \
   && (echo "$NAMES" | grep resources/holi-template.zip) \
   && (echo "$NAMES" | grep README.md)
then
  print "...DONE"
  exit 0
else
  printf "Missing expected changes - failed to prepare the release"
  exit 1
fi
