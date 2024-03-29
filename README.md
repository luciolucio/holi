# Holi

A library for calendar operations that are aware of weekends and holidays

[![build](https://github.com/luciolucio/holi/workflows/build-and-test/badge.svg)](https://github.com/luciolucio/holi/actions/workflows/build-and-test.yml)
[![Clojars Project](https://img.shields.io/clojars/v/io.github.luciolucio/holi.svg)](https://clojars.org/io.github.luciolucio/holi)
[![cljdoc badge](https://cljdoc.org/badge/io.github.luciolucio/holi)](https://cljdoc.org/d/io.github.luciolucio/holi)

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
see [I found a problem](#i-found-a-problem) below.

## Install

Import the latest version from [Clojars](https://clojars.org/io.github.luciolucio/holi) into your project dependencies.

ClojureScript is supported, but holi has only been tested when built with shadow-cljs and run on a
browser. It should still work otherwise, but if you have issues with other setups feel free to hit me up
on [Slack](http://clojurians.slack.com): `@Lucio Assis`

## Docs

[Full holi documentation](https://cljdoc.org/d/io.github.luciolucio/holi/CURRENT)

## I found a problem

Contribute a fix, or a new calendar to the project - see [CONTRIBUTING.md](CONTRIBUTING.md). You can also just let me
know on [Slack](http://clojurians.slack.com): `@Lucio Assis` and I'll try my best to fix it timely.

## I want custom holidays

Learn how
to [build yourself a custom library](https://cljdoc.org/d/io.github.luciolucio/holi/1.1.0/doc/custom-holidays), with
your own holiday calendars.
