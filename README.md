# Holi
[![build](https://github.com/luciolucio/holi/workflows/build-and-test/badge.svg)](https://github.com/luciolucio/holi/actions/workflows/build-and-test.yml)
[![Clojars Project](https://img.shields.io/clojars/v/io.github.luciolucio/holi.svg)](https://clojars.org/io.github.luciolucio/holi)

Holi is a clojure library for working with non-business days

```clojure
(ns my-app
  (:require [luciolucio.holi.core :as holi]
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

## Usage

* Import the latest version from [clojars](https://clojars.org/io.github.luciolucio/holi) in your project
* Require the `luciolucio.holi.core` namespace
* Ready to go!

## API

### add

```clojure
(add [date n unit & calendars])
```

Adds `n` of `unit` to `date` and returns a new date. Skips holidays in `calendars` when `unit` is `:business-days`.

| Parameter   | Description                                                                | Example                                                 |
|-------------|----------------------------------------------------------------------------|---------------------------------------------------------|
| `date`      | An instance of `java.time.LocalDate` or `java.time.LocalDateTime`          | `(LocalDate/of 2020 10 9)`                              |
| `n`         | An integer                                                                 | `2`, `-1`, `0`                                          |
| `unit`      | Unit of `n`                                                                | `:days` `:weeks` `:months` `:years` or `:business-days` |
| `calendars` | One or more strings representing holiday calendars (`:business-days` only) | `"US"`, `"BR"`                                          |

#### Notes
1. Types are preserved, i.e., passing a `LocalDate` in will return a `LocalDate`
2. The time portion is never altered on a `LocalDateTime` instance

### weekend?

```clojure
(weekend? [date])
```

Returns true if date is in a weekend, and false otherwise

| Parameter   | Description                                                                | Example                    |
|-------------|----------------------------------------------------------------------------|----------------------------|
| `date`      | An instance of `java.time.LocalDate` or `java.time.LocalDateTime`          | `(LocalDate/of 2020 10 9)` |

#### Notes

1. Weekend days are assumed to be Saturday and Sunday

### holiday?

```clojure
(holiday? [date calendar])
```

Returns true if date is a holiday in the given calendar, and false otherwise

| Parameter  | Description                                                       | Example                    |
|------------|-------------------------------------------------------------------|----------------------------|
| `date`     | An instance of `java.time.LocalDate` or `java.time.LocalDateTime` | `(LocalDate/of 2020 10 9)` |
| `calendar` | A string representing a holiday calendar                          | `"US"`, `"BR"`             |

### non-business-day?

```clojure
(non-business-day? [date & calendars])
```

Returns true only if date is whether in a weekend or a holiday in one of the given calendars. Returns false otherwise.

| Parameter   | Description                                                       | Example                                                 |
|-------------|-------------------------------------------------------------------|---------------------------------------------------------|
| `date`      | An instance of `java.time.LocalDate` or `java.time.LocalDateTime` | `(LocalDate/of 2020 10 9)`                              |
| `calendars` | One or more strings representing holiday calendars                | `"US"`, `"BR"`                                          |

### business-day?

```clojure
(business-day? [date & calendars])
```

Returns true only if date is not in a weekend and also not a holiday in any of the given calendars. Returns false otherwise.

| Parameter   | Description                                                       | Example                                                 |
|-------------|-------------------------------------------------------------------|---------------------------------------------------------|
| `date`      | An instance of `java.time.LocalDate` or `java.time.LocalDateTime` | `(LocalDate/of 2020 10 9)`                              |
| `calendars` | One or more strings representing holiday calendars                | `"US"`, `"BR"`                                          |


## Tips and tricks

### "Same or next" business day

Use `n = 0` to get _same or next_ semantics, that is:

- If the date is a business day already, just return the same date
- Otherwise it's as if you had used `n = 1`

```clojure
(ns my-app
  (:require [luciolucio.holi.core :as holi]
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

| Holiday calendar | Description                     |
|------------------|---------------------------------|
| US               | Official United States holidays |
| BR               | Brazilian holidays              |

## There is no holiday calendar for &lt;insert location here&gt;!

You have two options:

* **Contribute** a new calendar to the project (see [CONTRIBUTING.md](CONTRIBUTING.md))
* Build yourself a **custom library** with your own holiday calendars (it's easy! See [CUSTOM.md](CUSTOM.md]))
