(ns luciolucio.holi
  (:require [luciolucio.holi.core :as core]
            [luciolucio.holi.constants :as constants]
            [tick.core :as t]))

(defn add
  "Adds `n` of `unit` to `date` and returns a new date. Skips holidays in `calendars` when `unit` is `:business-days`.

  | Parameter   | Description                                                                | Example                                                 |
  |-------------|----------------------------------------------------------------------------|---------------------------------------------------------|
  | `date`      | An instance of `java.time.LocalDate` or `java.time.LocalDateTime`          | `(LocalDate/of 2020 10 9)`                              |
  | `n`         | An integer                                                                 | `2`, `-1`, `0`                                          |
  | `unit`      | Unit of `n`                                                                | `:days` `:weeks` `:months` `:years` or `:business-days` |
  | `calendars` | One or more strings representing holiday calendars (`:business-days` only) | `" US "`, `" BR "`                                          |

  **Notes**
  1. Types are preserved, i.e., passing a `LocalDate` in will return a `LocalDate`
  2. The time portion is never altered on a `LocalDateTime` instance
  3. `unit` will accept both `:weeks` and `:week` etc. That way you can say `1 :week` or `1 :business-day`."
  [date n unit & calendars]
  (core/validate-input date n unit)
  (if (contains? #{:business-days :business-day} unit)
    (core/add-with-calendars date n calendars)
    (t/>> date (t/new-period n (get core/unit->tick-unit unit)))))

(defn weekend?
  "Returns true if date is in a weekend, and false otherwise

  | Parameter | Description                                                       | Example                    |
  |-----------|-------------------------------------------------------------------|----------------------------|
  | `date`    | An instance of `java.time.LocalDate` or `java.time.LocalDateTime` | `(LocalDate/of 2020 10 9)` |

  **Notes**
  1. Weekend days are assumed to be Saturday and Sunday"
  [date]
  (let [weekend-days (core/read-calendar constants/WEEKEND-FILE-NAME)]
    (core/is-date-in-list? date weekend-days)))

(defn holiday?
  "Returns true if date is a holiday in the given calendar, and false otherwise

  | Parameter  | Description                                                       | Example                    |
  |------------|-------------------------------------------------------------------|----------------------------|
  | `date`     | An instance of `java.time.LocalDate` or `java.time.LocalDateTime` | `(LocalDate/of 2020 10 9)` |
  | `calendar` | A string representing a holiday calendar                          | `" US "`, `" BR "`             |"
  [date calendar]
  (let [holidays (core/read-calendar calendar)]
    (core/is-date-in-list? date holidays)))

(defn non-business-day?
  "Returns true only if date is whether in a weekend or a holiday in one of the given calendars. Returns false otherwise.

  | Parameter   | Description                                                       | Example                    |
  |-------------|-------------------------------------------------------------------|----------------------------|
  | `date`      | An instance of `java.time.LocalDate` or `java.time.LocalDateTime` | `(LocalDate/of 2020 10 9)` |
  | `calendars` | One or more strings representing holiday calendars                | `" US "`, `" BR "`             |"
  [date & calendars]
  (let [non-business-days (core/read-calendars (set (conj calendars constants/WEEKEND-FILE-NAME)))]
    (core/is-date-in-list? date non-business-days)))

(defn business-day?
  "Returns true only if date is not in a weekend and also not a holiday in any of the given calendars. Returns false otherwise.

  | Parameter   | Description                                                       | Example                    |
  |-------------|-------------------------------------------------------------------|----------------------------|
  | `date`      | An instance of `java.time.LocalDate` or `java.time.LocalDateTime` | `(LocalDate/of 2020 10 9)` |
  | `calendars` | One or more strings representing holiday calendars                | `" US "`, `" BR "`             |"
  [date & calendars]
  (not (apply non-business-day? date calendars)))
