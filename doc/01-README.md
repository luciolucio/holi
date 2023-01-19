# Holi

Holi is a Clojure and ClojureScript library for working with non-business days

## Examples

```clojure
(ns my-app
  (:require [luciolucio.holi :as holi]
    [tick.core :as t]))

;      July 2019
; Su Mo Tu We Th Fr Sa
;     1  2  3  4  5  6
;  7  8  9 10 11 12 13
; 14 15 16 17 18 19 20
; 21 22 23 24 25 26 27
; 28 29 30 31

(holi/add (t/date "2019-07-12") 3 :business-days) ; 2020-07-17 (skips weekends)
(holi/add (t/date "2019-07-03") 1 :business-days "US") ; 2019-07-05 (skips 4th of July as a US holiday)

(holi/weekend? (t/date "2019-07-06")) ; -> true
(holi/holiday? (t/date "2019-07-04") "US") ; -> true
```

Use of [juxt/tick](https://github.com/juxt/tick) is not required (but highly recommended).

## Install

Import the latest version from [Clojars](https://clojars.org/io.github.luciolucio/holi) into your project dependencies.

ClojureScript is supported, but holi has only been tested when built with shadow-cljs and run on a
browser. It should still work otherwise, but if you have issues with other setups, feel free to hit me up
on [Slack](http://clojurians.slack.com): `@Lucio Assis`

## Usage

See the [API docs](https://cljdoc.org/d/io.github.luciolucio/holi/0.13.3/api/luciolucio.holi) for detail on holi's
utilities: `add`, `weekend?`, `holiday?`, `business-day?` and `non-business-day?`.

## A note on terminology

The term `holiday calendar` does not mean a calendar proper, as in "The 2022 calendar". For example, the
`US holiday calendar` contains a rule that says "July 4th is a holiday, unless it falls on a weekend.
If it's a Saturday (Sunday), the holiday will be observed the previous Friday (next Monday)". Apply that to 2022 and
you get July 4th proper as a holiday, but for 2021 you'll get July 5th. A `holiday calendar` is not a collection of
dates, but **a set of rules that specify certain dates as holidays on any given year**.
