(ns {{lib-ns}}.{{lib-name}}
  (:require [com.piposaude.relative-date-add :as calenjars]))

(defn relative-date-add [date n unit & calendars]
  (apply calenjars/relative-date-add date n unit calendars))

(defn weekend? [date]
  (calenjars/weekend? date))

(defn holiday? [date calendar]
  (calenjars/holiday? date calendar))

(defn non-business-day? [date & calendars]
  (apply calenjars/non-business-day? date calendars))

(defn business-day? [date & calendars]
  (apply calenjars/business-day? date calendars))
