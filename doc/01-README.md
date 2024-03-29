# Holi

A library for calendar operations that are aware of weekends and holidays

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

## Features

* Standard calendar operations (e.g. `add 5 days`) that are aware of weekends and holidays to skip those when requested
* Boolean fns that check individual dates (`weekend?`, `holiday?`, `business-day?` and `non-business-day?`)
* List holidays for a certain year, or a specific date
* Non-standard weekends (supports sat/sun and fri/sat)
* Clojure and ClojureScript
* Customize holidays if needed

## Disclaimer

Holidays in holi have been put together to the best of the author's knowledge, and are not guaranteed to be neither
correct, complete nor historically accurate. You are encouraged to double-check
the [calendar showcase](https://luciolucio.github.io/holi/), the `.hol` files and perhaps the source to see if the
calendar you want to use fits your use case. If you think there's a mistake in a calendar,
see [the 'I found a problem' section in the GitHub repo's README](https://github.com/luciolucio/holi#i-found-a-problem).

## Install

Import the latest version from [Clojars](https://clojars.org/io.github.luciolucio/holi) into your project dependencies.

ClojureScript is supported, but holi has only been tested when built with shadow-cljs and run on a
browser. It should still work otherwise, but if you have issues with other setups feel free to hit me up
on [Slack](http://clojurians.slack.com): `@Lucio Assis`

## Usage

See the [API docs](https://cljdoc.org/d/io.github.luciolucio/holi/1.1.0/api/luciolucio.holi) for detail on holi's
utilities: `add`, `weekend?`, `holiday?`, `business-day?` and `non-business-day?`.

## A note on terminology

A `holiday calendar` is not a collection of dates, but **a set of rules that specify certain dates as holidays on any
given year**. The term `holiday calendar` does not mean a calendar proper, as in "The 2022 calendar". For example, the
`US holiday calendar` contains a rule that says "July 4th is a holiday, unless it falls on a weekend.
If it's a Saturday (Sunday), the holiday will be observed the previous Friday (next Monday)". Apply that to 2022, and
you get July 4th proper as a holiday, but for 2021 you'll get July 5th.
