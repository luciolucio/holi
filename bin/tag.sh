#!/usr/bin/env zsh

VERSION_NUMBER=$(grep '^[*]' CHANGELOG.md | head -1 | awk -F' ' '{print $2}')

CHANGELOG=$(awk -v ver="$VERSION_NUMBER" '/^[*] / { if (p) { exit }; if ($2 == ver) { p=1 } } p && NF' CHANGELOG.md)
echo "$CHANGELOG" > tag.txt

git tag "$VERSION_NUMBER" -F tag.txt
git push origin "$VERSION_NUMBER"

rm tag.txt
