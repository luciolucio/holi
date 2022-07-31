# Calenjars
[![build](https://github.com/piposaude/calenjars/workflows/build-and-test/badge.svg)](https://github.com/piposaude/calenjars/actions/workflows/build-and-test.yml)
[![Clojars Project](https://img.shields.io/clojars/v/piposaude/calenjars.svg)](https://clojars.org/piposaude/calenjars)

Calenjars is a clojure library for working with non-business days

```clojure
(ns my-app
  (:require [piposaude.calenjars :as calendar]
            [tick.core :as t]))

;      July 2019
; Su Mo Tu We Th Fr Sa
;     1  2  3  4  5  6
;  7  8  9 10 11 12 13
; 14 15 16 17 18 19 20
; 21 22 23 24 25 26 27
; 28 29 30 31

(calendar/add (t/date "2019-07-12") 3 :business-days) ; 17Jul2020 (skips weekends)
(calendar/add (t/date "2019-07-03") 1 :business-days "US") ; 5Jul2019 (skips 4th of July as a US holiday)

(calendar/weekend? (t/date "2019-07-06")) ; -> true
(calendar/holiday? (t/date "2019-07-04") "US") ; -> true
```

## API

### Add

```clojure
(add [date n unit & calendars])
```

Adds `n` of `unit` to `date` and returns a new date. Skips holidays in `calendars` when `unit` is `:business-days`.

| Parameter   | Description                                                       | Example                                                 |
|-------------|-------------------------------------------------------------------|---------------------------------------------------------|
| `date`      | An instance of `java.time.LocalDate` or `java.time.LocalDateTime` | `(LocalDate/of 2020 10 9)  `                            |
| `n`         | An integer                                                        | `2`, `-1`, `0`                                          |
| `unit`      | Unit of `n`                                                       | `:days` `:weeks` `:months` `:years` or `:business-days` |
| `calendars` | One or more strings representing holiday calendars                | `"US"`, `"BR"`                                          |

Note that passing a `LocalDate` will get you another `LocalDate` in return. Same goes for `LocalDateTime` (the time component won't be changed).

## Tips and tricks

### "Next" business day

Use `n = 0` to get the _next_ business day. This could be the same day that was passed in.

```clojure
(ns my-app
  (:require [piposaude.calenjars :as calendar]
    [tick.core :as t]))

(calendar/add (t/date "2022-07-06") 0 :business-days) ; -> 8Jul22, because 6Jul22 is a Saturday
(calendar/add (t/date "2022-07-07") 0 :business-days) ; -> 8Jul22, because 7Jul22 is a Sunday
(calendar/add (t/date "2022-07-08") 0 :business-days) ; -> 8Jul22, as it's a regular Monday
```

## Available holiday calendars

| Holiday calendar | Description                     |
|------------------|---------------------------------|
| US               | Official United States holidays |
| BR               | Brazilian holidays              |
