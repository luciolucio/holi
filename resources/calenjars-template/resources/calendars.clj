(ns {{lib-ns}}
  (:require [com.piposaude.relative-date-add :as calenjars]))

(defn relative-date-add [date n unit & calendars]
  (apply calenjars/relative-date-add date n unit calendars))
