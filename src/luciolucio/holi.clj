(ns luciolucio.holi
  (:require [luciolucio.holi.core :as core]
            [luciolucio.holi.constants :as constants]
            [tick.alpha.api :as t]))

(defn add [date n unit & calendars]
  "Adds n units to date and returns a new date

  date must be an instance of java.time.LocalDate
  or java.time.LocalDateTime, n must be an integer
  and valid units are found in the units set"
  (core/validate-input date n unit)
  (if (contains? #{:business-days :business-day} unit)
    (core/add-with-calendars date n calendars)
    (t/+ date (t/new-period n (get core/unit->tick-unit unit)))))

(defn weekend? [date]
  "Returns true only if date is in a weekend"
  (let [weekend-days (core/read-calendar constants/WEEKEND-FILE-NAME)]
    (core/is-date-in-list? date weekend-days)))

(defn holiday? [date calendar]
  "Returns true only if date is a holiday in the given calendar"
  (let [holidays (core/read-calendar calendar)]
    (core/is-date-in-list? date holidays)))

(defn non-business-day? [date & calendars]
  "Returns true only if date is whether a weekend
  or a holiday in one of the given calendars"
  (let [non-business-days (core/read-calendars (set (conj calendars constants/WEEKEND-FILE-NAME)))]
    (core/is-date-in-list? date non-business-days)))

(defn business-day? [date & calendars]
  "Returns true only if date is not in a weekend
  and also not a holiday in any of the given calendars"
  (not (apply non-business-day? date calendars)))
