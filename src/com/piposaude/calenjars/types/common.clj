(ns com.piposaude.calenjars.types.common
  (:require [tick.alpha.api :as t]))

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

(defn- pad-with-zero [day-or-month]
  (if (= 1 (count day-or-month))
    (str "0" day-or-month)
    day-or-month))

(defn- format-month [month]
  (if (contains? month->month-number month)
    (month->month-number month)
    (pad-with-zero month)))

(defn holiday
  ([name day month year]
   (holiday name day month year false))
  ([name day month year exception?]
   (let [formatted-month (format-month month)
         formatted-day (pad-with-zero day)
         yyyy-mmm-dd (format "%s-%s-%s" year formatted-month formatted-day)]
     {:name name :date (t/date yyyy-mmm-dd) :exception? exception?})))
