(ns ^:no-doc luciolucio.holi.types.nth-day-of-week
  (:require [clojure.edn :as edn]
            [luciolucio.holi.types.common :as common]
            [tick.core :as t]
            [tick.alpha.interval :as t.i])
  (:import (java.time LocalDate)))

(def day-of-week-str->day-of-week
  {"Sun" t/SUNDAY
   "Mon" t/MONDAY
   "Tue" t/TUESDAY
   "Wed" t/WEDNESDAY
   "Thu" t/THURSDAY
   "Fri" t/FRIDAY
   "Sat" t/SATURDAY})

(def month->month-as-int
  {"Jan" 1
   "Feb" 2
   "Mar" 3
   "Apr" 4
   "May" 5
   "Jun" 6
   "Jul" 7
   "Aug" 8
   "Sep" 9
   "Oct" 10
   "Nov" 11
   "Dec" 12})

(defn get-holiday-nth-day-of-week [^Integer year holiday-name [i day-of-week-str month] start-year end-year]
  (let [i-as-int (edn/read-string i)
        day-of-week (day-of-week-str->day-of-week day-of-week-str)
        ^Integer month-as-int (-> month month->month-as-int)
        month-bounds (t.i/bounds (t/year-month (LocalDate/of year month-as-int 1)))
        month-days (t/range
                    (t/date (t/beginning month-bounds))
                    (t/date (t/end month-bounds))
                    (t/new-period 1 :days))
        month-days-that-are-day-of-week (filterv #(= (t/day-of-week %) day-of-week) month-days)
        index-nth (if (pos? i-as-int) (dec i-as-int) (+ (count month-days-that-are-day-of-week) i-as-int))
        holiday-date (when (contains? month-days-that-are-day-of-week index-nth) (nth month-days-that-are-day-of-week index-nth))
        holiday (when holiday-date (common/holiday holiday-name (str (t/day-of-month holiday-date)) (str month-as-int) year))]
    (cond
      (not holiday-date)
      nil

      (and (not start-year) (not end-year))
      holiday

      (and start-year (>= year start-year))
      holiday

      (and end-year (<= year end-year))
      holiday

      :else
      nil)))
