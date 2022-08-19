#!/usr/bin/env zsh

awk -v ver="$1" '/^[*] / { if (p) { exit }; if ($2 == ver) { p=1 } } p && NF' CHANGELOG.md
