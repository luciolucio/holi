#!/usr/bin/env bash

cd test-lib/cljs || exit
yarn install
clojure -M:shadow-cljs compile test
node out/tests.js
