(ns {{lib-ns}}.{{lib-name}}
  (:require [luciolucio.holi :as holi]))

(defn add
  ([date n unit]
   (holi/add date n unit))
  ([date n unit weekend-option & calendars]
   (apply holi/add date n unit weekend-option calendars)))

(defn weekend?
  ([date]
   (holi/weekend? date))
  ([date weekend-option]
   (holi/weekend? date weekend-option)))

(defn holiday? [date calendar]
  (holi/holiday? date calendar))

(defn non-business-day? [date & calendars]
  (apply holi/non-business-day? date calendars))

(defn business-day? [date & calendars]
  (apply holi/business-day? date calendars))

(defn holidays-in-year [year calendar]
  (holi/holidays-in-year year calendar))

(defn holidays-in-date [date calendar]
  (holi/holidays-in-date date calendar))
