(ns luciolucio.holi
  (:require [clojure.string :as cstr]
            [luciolucio.holi.core :as core]
            [luciolucio.holi.constants :as constants]
            [tick.core :as t]))

(defn- safe-calendars [calendars]
  (let [missing-calendars (core/missing-calendars calendars)]
    (if (seq missing-calendars)
      (throw (ex-info (str "Unknown calendar(s): " (cstr/join ", " missing-calendars)) {}))
      calendars)))

(defn- safe-calendar [calendar]
  (first (safe-calendars [calendar])))

(defn- solve-weekend-option-and-calendars [weekend-option-or-calendar calendars]
  (if (contains? constants/ALL-WEEKEND-OPTIONS weekend-option-or-calendar)
    [weekend-option-or-calendar calendars]
    [:sat-sun (conj calendars weekend-option-or-calendar)]))

(defn add
  "Adds `n` of `unit` to `date` and returns a new date. Skips any holidays in `calendars` when `unit` is `:business-days`.

  | Parameter        | Description                                                                | Examples                                                |
  |------------------|----------------------------------------------------------------------------|---------------------------------------------------------|
  | `date`           | An instance of `LocalDate` or `LocalDateTime`                              | `(LocalDate/of 2020 10 9)`                              |
  | `n`              | An integer                                                                 | `2`, `-1`, `0`                                          |
  | `unit`           | Unit of `n`                                                                | `:days` `:weeks` `:months` `:years` or `:business-days` |
  | `weekend-option` | Choice of days considered weekend days. Optional, defaults to :sat-sun     | `:sat-sun`, `:fri-sat`                                  |
  | `calendars`      | One or more strings representing holiday calendars (`:business-days` only) | `\"US\"`, `\"BR\"`                                      |

  Throws an ex-info if unit is `:business-days` and holi has no record of holidays for the year of the resulting date,
  or any of the given calendars is unknown.

  **Notes**
  1. Types are preserved, i.e., passing a `LocalDate` in will return a `LocalDate`
  2. The time portion is never altered on a `LocalDateTime` instance
  3. `LocalDate` and `LocalDateTime` refer whether to java.time or js-joda
  4. `unit` will accept both `:weeks` and `:week` etc. That way you can write `1 :week` or `1 :business-day`."
  {:arglists '([date n unit] [date n unit & calendars] [date n unit weekend-option] [date n unit weekend-option & calendars])}
  ([date n unit]
   (add date n unit :sat-sun))
  ([date n unit weekend-option & calendars]
   (let [[weekend-option calendars] (solve-weekend-option-and-calendars weekend-option calendars)]
     (core/validate-input date n unit)
     (if (contains? #{:business-days :business-day} unit)
       (core/add-with-calendars date n (safe-calendars calendars) weekend-option)
       (t/>> date (t/new-period n (get core/unit->tick-unit unit)))))))

(defn- safe-date
  "Returns `date` if it's within the boundaries of `all-dates` and throws otherwise"
  [all-dates date]
  (let [low-limit (when (seq all-dates) (t/first-day-of-year (first all-dates)))
        high-limit (when (seq all-dates) (t/last-day-of-year (last all-dates)))]
    (cond
      (and low-limit (t/< (t/date date) low-limit))
      (throw (ex-info "Date is out of bounds" {}))

      (and high-limit (t/> (t/date date) high-limit))
      (throw (ex-info "Date is out of bounds" {}))

      :else
      date)))

(defn weekend?
  "Returns true if date is in a weekend, and false otherwise

  | Parameter        | Description                                                                         | Examples                                     |
  |------------------|-------------------------------------------------------------------------------------|----------------------------------------------|
  | `date`           | An instance of `LocalDate`/`LocalDateTime` or a string that can be parsed as a date | `(LocalDate/of 2020 10 9)`, `\"2020-10-10\"` |
  | `weekend-option` | Choice of days considered weekend days. Optional, defaults to :sat-sun              | `:sat-sun`, `:fri-sat`                       |

  Throws an ex-info if holi has no record of weekends for the year of the given date"
  ([date]
   (weekend? date :sat-sun))
  ([date weekend-option]
   (let [weekend-days (core/read-dates (get constants/WEEKEND-FILE-NAMES weekend-option))]
     (core/is-date-in-list? (safe-date weekend-days date) weekend-days))))

(defn holiday?
  "Returns true if date is a holiday in the given calendar, and false otherwise

  | Parameter  | Description                                                                         | Examples                                     |
  |------------|-------------------------------------------------------------------------------------|----------------------------------------------|
  | `date`     | An instance of `LocalDate`/`LocalDateTime` or a string that can be parsed as a date | `(LocalDate/of 2020 10 9)`, `\"2020-10-09\"` |
  | `calendar` | A string representing a holiday calendar                                            | `\"US\"`, `\"BR\"`                           |

  Throws an ex-info if holi has no record of holidays for the year of the given date or the calendar is unknown"
  [date calendar]
  (let [holidays (core/read-dates (safe-calendar calendar))]
    (core/is-date-in-list? (safe-date holidays date) holidays)))

(defn non-business-day?
  "Returns true only if date is whether in a weekend or a holiday in one of the given calendars. Returns false otherwise.

  | Parameter        | Description                                                                         | Examples                                     |
  |------------------|-------------------------------------------------------------------------------------|----------------------------------------------|
  | `date`           | An instance of `LocalDate`/`LocalDateTime` or a string that can be parsed as a date | `(LocalDate/of 2020 10 9)`, `\"2020-10-09\"` |
  | `weekend-option` | Choice of days considered weekend days. Optional, defaults to :sat-sun              | `:sat-sun`, `:fri-sat`                       |
  | `calendars`      | One or more strings representing holiday calendars                                  | `\"US\"`, `\"BR\"`                           |

  Throws an ex-info if holi has no record of holidays or weekends for the year of the given date or any of the given calendars is unknown"
  {:arglists '([date] [date & calendars] [date weekend-option] [date weekend-option & calendars])}
  ([date]
   (non-business-day? date :sat-sun))
  ([date weekend-option & calendars]
   (let [[weekend-option calendars] (solve-weekend-option-and-calendars weekend-option calendars)
         non-business-days (core/read-dates (set (conj (safe-calendars calendars) (get constants/WEEKEND-FILE-NAMES weekend-option))))]
     (core/is-date-in-list? (safe-date non-business-days date) non-business-days))))

(defn business-day?
  "Returns true only if date is not in a weekend and also not a holiday in any of the given calendars. Returns false otherwise.

  | Parameter        | Description                                                                         | Examples                                     |
  |------------------|-------------------------------------------------------------------------------------|----------------------------------------------|
  | `date`           | An instance of `LocalDate`/`LocalDateTime` or a string that can be parsed as a date | `(LocalDate/of 2020 10 9)`, `\"2020-10-09\"` |
  | `weekend-option` | Choice of days considered weekend days. Optional, defaults to :sat-sun              | `:sat-sun`, `:fri-sat`                       |
  | `calendars`      | One or more strings representing holiday calendars                                  | `\"US\"`, `\"BR\"`                           |

  Throws an ex-info if holi has no record of holidays or weekends for the year of the given date or the calendar is unknown"
  {:arglists '([date] [date & calendars] [date weekend-option] [date weekend-option & calendars])}
  ([date]
   (business-day? date :sat-sun))
  ([date weekend-option & calendars]
   (not (apply non-business-day? date weekend-option calendars))))

(defn holidays-in-year
  "Returns a collection of maps of the form `{:name \"Name\" :date LocalDate}`
  where every item of the collection represents a holiday in the given year according
  to the given holiday calendar

  | Parameter  | Description                                   | Examples                   |
  |------------|-----------------------------------------------|----------------------------|
  | `year`     | An integer or string that represents a year   | `2030`, `\"1982\"`         |
  | `calendar` | A string representing a holiday calendar      | `\"US\"`, `\"BR\"`         |

  Throws an ex-info if holi has no record of weekends or holidays for the given year or the calendar is unknown"
  [year calendar]
  (core/read-calendar (safe-calendar calendar) year))

(defn holidays-in-date
  "Returns a collection containing names of holidays in the given date, for example: `[\"New Year's Day\" \"Independence Day\"]`

  If there are none, it will return an empty collection.

  | Parameter  | Description                                                                         | Examples                                     |
  |------------|-------------------------------------------------------------------------------------|----------------------------------------------|
  | `date`     | An instance of `LocalDate`/`LocalDateTime` or a string that can be parsed as a date | `(LocalDate/of 2020 10 9)`, `\"2020-10-09\"` |
  | `calendar` | A string representing a holiday calendar                                            | `\"US\"`, `\"BR\"`                           |

  Throws an ex-info if holi has no record of holidays for the year of the given date or the calendar is unknown"
  [date calendar]
  (if (holiday? date calendar)
    (->> calendar
         (holidays-in-year (-> date t/date t/year))
         (filter #(= (t/date date) (:date %)))
         (map :name))
    []))
