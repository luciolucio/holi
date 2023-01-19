# Holi

Holi is a Clojure and ClojureScript library for working with non-business days

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

(holi/add (t/date "2019-07-12") 3 :business-days) ; 17Jul2020 (skips weekends)
(holi/add (t/date "2019-07-03") 1 :business-days "US") ; 5Jul2019 (skips 4th of July as a US holiday)

(holi/weekend? (t/date "2019-07-06")) ; -> true
(holi/holiday? (t/date "2019-07-04") "US") ; -> true
```

Use of [juxt/tick](https://github.com/juxt/tick) is not required (but highly recommended).

## Installation

Import the latest version from [clojars](https://clojars.org/io.github.luciolucio/holi) into your project dependencies

### ClojureScript support

Holi has been successfully tested when built with shadow-cljs and run on a browser. If you have issues with other setups,
hit me up on [Slack](http://clojurians.slack.com): `@Lucio Assis`

## Usage

Visit [cljdoc](https://cljdoc.org/d/io.github.luciolucio/holi/0.13.2/api/luciolucio.holi) for the public API docs

## Tips and tricks

### "Same or next" business day

Use `n = 0` to get _same or next_ semantics, that is:

- If the date is a business day already, just return the same date
- Otherwise it's as if you had used `n = 1`

```clojure
(ns my-app
  (:require [luciolucio.holi :as holi]
    [tick.core :as t]))

(holi/add (t/date "2022-07-06") 0 :business-days) ; -> 8Jul22, because 6Jul22 is a Saturday
(holi/add (t/date "2022-07-07") 0 :business-days) ; -> 8Jul22, because 7Jul22 is a Sunday
(holi/add (t/date "2022-07-08") 0 :business-days) ; -> 8Jul22, as it's a regular Monday
```

One way this is useful is for things like "This is due next month, on the same day of the month, but if it's a weekend then it's the following business day":

```clojure
(-> (t/date "2022-06-06")
    (holi/add 1 :months)         ; 6Jul22, Sat
    (holi/add 0 :business-days)) ; 8Jul22, Mon
```

## Available holiday calendars

| Holiday calendar | Description             |
|------------------|-------------------------|
| US               | United States holidays  |
| GB               | Great Britain holidays  |
| BR               | Brazilian holidays      |
| brazil/sao-paulo | São Paulo city holidays |

## FAQ

**What do I do if a date is wrong or a holiday calendar is missing for &lt;insert location here&gt;?**

You have two options:

* **Contribute** a fix, or a new calendar to the project (see [CONTRIBUTING.md](CONTRIBUTING.md))
* Build yourself a **custom library**, with your own holiday calendars (it's easy! See [CUSTOM.md](doc/CUSTOM.md))
