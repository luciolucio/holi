(ns {{lib-ns}}.{{lib-name}}
  (:require [luciolucio.holi :as holi]))

(defn add
  {:arglists '([date n unit] [date n unit & calendars] [date n unit weekend-option] [date n unit weekend-option & calendars])}
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

(defn non-business-day?
  {:arglists '([date] [date & calendars] [date weekend-option] [date weekend-option & calendars])}
  ([date]
   (holi/non-business-day? date))
  ([date weekend-option & calendars]
   (apply holi/non-business-day? date weekend-option calendars)))

(defn business-day?
  {:arglists '([date] [date & calendars] [date weekend-option] [date weekend-option & calendars])}
  ([date]
   (holi/business-day? date))
  ([date weekend-option & calendars]
   (apply holi/business-day? date weekend-option calendars)))

(defn holidays-in-year [year calendar]
  (holi/holidays-in-year year calendar))

(defn holidays-in-date [date calendar]
  (holi/holidays-in-date date calendar))
