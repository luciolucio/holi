#!/usr/bin/env bash

declare -a pairs

cd resources/calendars-generated || exit

shopt -s globstar
for full_path in **/*.datelist
do
  key=${full_path/\.datelist/}
  pairs+=("\"$key\"+(rc/inline+\"$full_path\")")
done

cd ../..

formatted_key_value_pairs=$(echo "${pairs[*]}" | tr ' ' '\n' | tr '+' ' ')
printf "(def holiday-strings\n{%s})" "$formatted_key_value_pairs" > holiday-strings-code

sed -e '/^;; HOLIDAY-STRINGS-END/r holiday-strings-code' -e '/^;; HOLIDAY-STRINGS-BEGIN/,/^;; HOLIDAY-STRINGS-END/d' src/luciolucio/holi/core.cljc > temp

mv temp src/luciolucio/holi/core.cljc
rm holiday-strings-code
