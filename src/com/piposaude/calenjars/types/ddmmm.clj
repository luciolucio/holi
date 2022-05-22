(ns com.piposaude.calenjars.types.ddmmm
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [com.piposaude.calenjars.types.common :as common]
            [tick.alpha.api :as t])
  (:import (java.time.format DateTimeParseException)))

(defn get-holiday-ddmm [year name [day month] observed start-year end-year]
  (try
    (let [holiday (common/holiday name day month year)]
      (cond
        (and start-year (< year start-year))
        nil

        (and end-year (> year end-year))
        nil

        (and observed (= t/SATURDAY (t/day-of-week (:date holiday))))
        (update holiday :date #(t/- % (t/new-period 1 :days)))

        (and observed (= t/SUNDAY (t/day-of-week (:date holiday))))
        (update holiday :date #(t/+ % (t/new-period 1 :days)))

        :else
        holiday))
    (catch DateTimeParseException e
      (if (str/includes? (.getMessage e) "not a leap year")
        nil
        (throw e)))))
