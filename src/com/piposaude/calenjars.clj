(ns com.piposaude.calenjars
  (:require [tick.alpha.api :as t]
            [clojure.java.io :as io]
            [clojure.string :as str])
  (:import (java.time LocalDate LocalDateTime)))

(def WEEKEND-FILE-NAME "WEEKEND")

(def units #{:days :weeks :months :years :business-days})

(defn- validate-input [date n unit]
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

(def read-calendars
  (memoize
   (fn [calendars]
     (->> calendars
          (keep read-calendar)
          flatten
          sort
          dedupe))))

(defn- sign [n]
  (if (pos? n) 1 -1))

(defn- get-step [n]
  (if (zero? n)
    0
    (sign n)))

(defn- abs [x]
  (if pos? x (- x)))

(defn- is-date-in-list? [date list]
  (boolean (some #{(t/date date)} list)))

(defn- inc-unless-holiday [date non-business-days days-added n]
  (if (is-date-in-list? date non-business-days)
    days-added
    (+ days-added (sign n))))

(defn- add-with-calendars [date n calendars]
  (let [non-business-days (read-calendars (set (conj calendars WEEKEND-FILE-NAME)))
        step (t/new-period (get-step n) :days)]
    (if (= n 0)
      (if (is-date-in-list? date non-business-days)
        (add-with-calendars date 1 calendars)
        date)
      (loop [candidate date
             days-added 0]
        (if (= (abs n) days-added)
          candidate
          (let [new-date (t/+ candidate step)
                m (inc-unless-holiday new-date non-business-days days-added n)]
            (recur new-date m)))))))

(defn relative-date-add [date n unit & calendars]
  "Adds n 'unit's to date and returns a new date

  date must be an instance of java.time.LocalDate
  or java.time.LocalDateTime, n must be an integer
  and valid units are found in the units set"
  (validate-input date n unit)
  (if (= unit :business-days)
    (add-with-calendars date n calendars)
    (t/+ date (t/new-period n unit))))

(defn weekend? [date]
  "Returns true only if date is in a weekend"
  (let [weekend-days (read-calendar WEEKEND-FILE-NAME)]
    (is-date-in-list? date weekend-days)))

(defn holiday? [date calendar]
  "Returns true only if date is a holiday in the given calendar"
  (let [holidays (read-calendar calendar)]
    (is-date-in-list? date holidays)))

(defn non-business-day? [date & calendars]
  "Returns true only if date is whether a weekend
  or a holiday in one of the given calendars"
  (let [non-business-days (read-calendars (set (conj calendars WEEKEND-FILE-NAME)))]
    (is-date-in-list? date non-business-days)))

(defn business-day? [date & calendars]
  "Returns true only if date is not in a weekend
  and also not a holiday in any of the given calendars"
  (not (apply non-business-day? date calendars)))
