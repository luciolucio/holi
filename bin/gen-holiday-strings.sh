#!/usr/bin/env bash

GENERATE_FOR_LOCAL_USE=$1

if [ -z "$GENERATE_FOR_LOCAL_USE" ]
then
  CALENDAR_PATH="resources/calendars-generated"
  RETURN_PATH="../.."
else
  CALENDAR_PATH="resources"
  RETURN_PATH=".."
fi

declare -a pairs

cd $CALENDAR_PATH || exit

shopt -s globstar
for full_path in **/*.datelist
do
  path_no_suffix=${full_path/\.datelist/}
  key=${path_no_suffix/calendars-generated\//}
  pairs+=("\"$key\"+(rc/inline+\"$full_path\")")
done

cd $RETURN_PATH || exit

formatted_key_value_pairs=$(echo "${pairs[*]}" | tr ' ' '\n' | tr '+' ' ')
printf "(def holiday-strings\n{%s})" "$formatted_key_value_pairs" > holiday-strings-code

sed -e '/^;; HOLIDAY-STRINGS-END/r holiday-strings-code' -e '/^;; HOLIDAY-STRINGS-BEGIN/,/^;; HOLIDAY-STRINGS-END/d' src/luciolucio/holi/core.cljc > temp

mv temp src/luciolucio/holi/core.cljc
rm holiday-strings-code
