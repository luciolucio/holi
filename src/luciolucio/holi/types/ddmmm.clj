(ns luciolucio.holi.types.ddmmm
  (:require [clojure.string :as cstr]
            [luciolucio.holi.types.common :as common]
            [tick.core :as t])
  (:import (java.time.format DateTimeParseException)))

(defn get-holiday-ddmm [year name [day month] observation-rule start-year end-year]
  (try
    (let [holiday (common/holiday name day month year)]
      (cond
        (and start-year (< year start-year))
        nil

        (and end-year (> year end-year))
        nil

        (and (= observation-rule :observed) (= t/SATURDAY (t/day-of-week (:date holiday))))
        (update holiday :date #(t/<< % (t/new-period 1 :days)))

        (and (= observation-rule :observed) (= t/SUNDAY (t/day-of-week (:date holiday))))
        (update holiday :date #(t/>> % (t/new-period 1 :days)))

        (and (= observation-rule :observed-monday) (= t/SATURDAY (t/day-of-week (:date holiday))))
        (update holiday :date #(t/>> % (t/new-period 2 :days)))

        (and (= observation-rule :observed-monday) (= t/SUNDAY (t/day-of-week (:date holiday))))
        (update holiday :date #(t/>> % (t/new-period 1 :days)))

        (and (= observation-rule :observed-monday-tuesday) (contains? #{t/SATURDAY t/SUNDAY} (t/day-of-week (:date holiday))))
        (update holiday :date #(t/>> % (t/new-period 2 :days)))

        :else
        holiday))
    (catch DateTimeParseException e
      (if (cstr/includes? (.getMessage e) "not a leap year")
        nil
        (throw e)))))
