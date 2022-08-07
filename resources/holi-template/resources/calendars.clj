(ns {{lib-ns}}.{{lib-name}}
  (:require [luciolucio.holi :as holi]))

(defn add [date n unit & calendars]
  (apply holi/add date n unit calendars))

(defn weekend? [date]
  (holi/weekend? date))

(defn holiday? [date calendar]
  (holi/holiday? date calendar))

(defn non-business-day? [date & calendars]
  (apply holi/non-business-day? date calendars))

(defn business-day? [date & calendars]
  (apply holi/business-day? date calendars))
