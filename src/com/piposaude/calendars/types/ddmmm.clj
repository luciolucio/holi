(ns com.piposaude.calendars.types.ddmmm
  (:require [clojure.string :as str]
            [tick.core :as t])
  (:import (java.time.format DateTimeParseException)))

(def month->month-number
  {"Jan" "01"
   "Feb" "02"
   "Mar" "03"
   "Apr" "04"
   "May" "05"
   "Jun" "06"
   "Jul" "07"
   "Aug" "08"
   "Sep" "09"
   "Oct" "10"
   "Nov" "11"
   "Dec" "12"})

(defn- format-month [month]
  (month->month-number month))

(defn- format-day [day]
  (if (= 1 (count day))
    (str "0" day)
    day))

(defn- holiday [name date]
  {:name name :date date})

(defn get-holiday-ddmm [year name [day month]]
  (let [formatted-month (format-month month)
        formatted-day (format-day day)
        yyyy-mmm-dd (format "%s-%s-%s" year formatted-month formatted-day)]
    (try
      (holiday name (t/date yyyy-mmm-dd))
      (catch DateTimeParseException e
        (if (str/includes? (.getMessage e) "not a leap year")
          nil
          (throw e))))))
