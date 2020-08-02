(ns com.piposaude.relative-date-add
  (:require [tick.core :as t]
            [clojure.java.io :as io]
            [clojure.string :as str])
  (:import (java.time LocalDate LocalDateTime)))

(def units #{:days :weeks :months :years :business-days})

(defn validate-input [date n unit]
  (when-not (or (instance? LocalDate date) (instance? LocalDateTime date))
    (throw (IllegalArgumentException. (str "Illegal date: " date))))
  (when-not (integer? n)
    (throw (IllegalArgumentException. (str "Illegal n: " n))))
  (when-not (contains? units unit)
    (throw (IllegalArgumentException. (str "Unrecognized unit: " unit)))))

(def read-calendar
  (memoize
    (fn [calendar]
      (let [holiday (io/resource calendar)
            holiday-strings (some-> holiday
                                    slurp
                                    (str/split #"\n"))]
        (when holiday-strings
          (map t/date holiday-strings))))))

(defn read-calendars [calendars]
  (->> calendars
       (keep read-calendar)
       flatten
       sort))

(defn sign [n]
  (if (pos? n) 1 -1))

(defn get-step [n]
  (if (zero? n)
    0
    (sign n)))

(defn abs [x]
  (if pos? x (- x)))

(defn inc-unless-holiday [date non-business-days days-added n]
  (if (some #{(t/date date)} non-business-days)
    days-added
    (+ days-added (sign n))))

(defn add-with-calendars [date n calendars]
  (let [non-business-days (read-calendars (set (conj calendars "WEEKEND")))
        step (t/new-period (get-step n) :days)]
    (loop [candidate date
           days-added 0]
      (if (= (abs n) days-added)
        candidate
        (let [new-date (t/+ candidate step)
              m (inc-unless-holiday new-date non-business-days days-added n)]
          (recur new-date m))))))

(defn relative-date-add [date n unit & calendars]
  "Adds n 'unit's to date and returns a new date

  date must be an instance of java.time.LocalDate
  or java.time.LocalDateTime, n must be an integer
  and valid units are found in the units set"
  (validate-input date n unit)
  (if (= unit :business-days)
    (add-with-calendars date n calendars)
    (t/+ date (t/new-period n unit))))
