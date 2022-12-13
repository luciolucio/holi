#!/usr/bin/env zsh

declare -a pairs

cd resources/calendars-generated || exit

for full_path in **/*.datelist
do
  key=${full_path/\.datelist/}
  pairs+=("\"$key\"+(rc/inline+\"$full_path\")")
done

cd ../..

formatted_key_value_pairs=$(echo "${pairs[@]}" | tr ' ' '\n' | tr '+' ' ')
print "(def holiday-strings\n{$formatted_key_value_pairs})" > holiday-strings-code

sed -e '/^;; HOLIDAY-STRINGS-END/r holiday-strings-code' -e '/^;; HOLIDAY-STRINGS-BEGIN/,/^;; HOLIDAY-STRINGS-END/d' src/luciolucio/holi/core.cljc > temp

mv temp src/luciolucio/holi/core.cljc
rm holiday-strings-code
