(ns com.piposaude.calenjars.types.rules.nth-day-of-week
  (:require [clojure.edn :as edn]
            [com.piposaude.calenjars.types.common :as common]
            [tick.alpha.api :as t])
  (:import (java.time LocalDate)))

(def day-of-week-str->day-of-week
  {"Sun" t/SUNDAY
   "Mon" t/MONDAY
   "Tue" t/TUESDAY
   "Wed" t/WEDNESDAY
   "Thu" t/THURSDAY
   "Fri" t/FRIDAY
   "Sat" t/SATURDAY})

(defn get-holiday-nth-day-of-week [^Integer year holiday-name [i day-of-week-str month]]
  (let [i-as-int (edn/read-string i)
        day-of-week (day-of-week-str->day-of-week day-of-week-str)
        ^Integer month-as-int (-> month common/month->month-number edn/read-string)
        month-bounds (t/bounds (t/year-month (LocalDate/of year month-as-int 1)))
        month-days (t/range
                    (t/date (t/beginning month-bounds))
                    (t/date (t/end month-bounds))
                    (t/new-period 1 :days))
        month-days-that-are-day-of-week (filterv #(= (t/day-of-week %) day-of-week) month-days)
        index (if (pos? i-as-int) (dec i-as-int) (+ (count month-days-that-are-day-of-week) i-as-int))
        holiday (when (contains? month-days-that-are-day-of-week index) (nth month-days-that-are-day-of-week index))]
    (when holiday
      (common/holiday holiday-name (str (t/day-of-month holiday)) (str month-as-int) year))))
