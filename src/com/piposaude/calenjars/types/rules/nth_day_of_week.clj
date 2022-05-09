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
  (let [^int month-as-int (-> month common/month->month-number edn/read-string)
        i-as-int (edn/read-string i)
        intvl (t/bounds (t/year-month (LocalDate/of year month-as-int 1)))
        week-beginnings (t/range
                          (t/date (t/beginning intvl))
                          (t/date (t/end intvl))
                          (t/new-period 1 :weeks))
        days-in-week (t/range
                       (nth week-beginnings (dec i-as-int))
                       (nth week-beginnings i-as-int)
                       (t/new-period 1 :days))
        day (first (filter #(= (t/day-of-week %) (day-of-week-str->day-of-week day-of-week-str)) days-in-week))]
    (common/holiday holiday-name (str (t/day-of-month day)) (str month-as-int) year)))
