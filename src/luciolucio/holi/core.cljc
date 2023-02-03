(ns ^:no-doc luciolucio.holi.core
  (:require [clojure.string :as cstr]
            [luciolucio.holi.constants :as constants]
            [tick.core :as t]
            [luciolucio.holi.util :as util :include-macros true]))

(def unit->tick-unit {:days          :days
                      :weeks         :weeks
                      :months        :months
                      :years         :years
                      :business-days :business-days
                      :day           :days
                      :week          :weeks
                      :month         :months
                      :year          :years
                      :business-day  :business-days})

(def valid-units (set (keys unit->tick-unit)))

(defn validate-input [date n unit]
  (when-not (or (t/date? date) (t/date-time? date))
    (throw (ex-info (str "Illegal date: " date) {})))
  (when-not (integer? n)
    (throw (ex-info (str "Illegal n: " n) {})))
  (when-not (contains? valid-units unit)
    (throw (ex-info (str "Unrecognized unit: " unit) {}))))

(def holiday-datelists
  "Map from holiday name to datelists

  Run `make gen-holidays` to generate these files"
  {"WEEKEND"          (util/slurp-resource "calendars-generated/WEEKEND.datelist")
   "US"               (util/slurp-resource "calendars-generated/US.datelist")
   "GB"               (util/slurp-resource "calendars-generated/GB.datelist")
   "BR"               (util/slurp-resource "calendars-generated/BR.datelist")
   "brazil/sao-paulo" (util/slurp-resource "calendars-generated/brazil/sao-paulo.datelist")})

(defn timestamp->date [timestamp]
  (t/>> constants/TIMESTAMP-REFERENCE-DATE (t/new-period (dec timestamp) :days)))

(defn remove-leading-zeroes [s]
  (cstr/replace s #"^0+" ""))

(def WEEKEND-STRING-LENGTH 4)
(def HOLIDAY-STRING-LENGTH 6)
(def HOLIDAY-STRING-TIMESTAMP-BEGIN 0)
(def HOLIDAY-STRING-TIMESTAMP-END 4)
(def HOLIDAY-STRING-INDICATOR-BEGIN 4)
(def HOLIDAY-STRING-INDICATOR-LENGTH 2)
(def HOLIDAY-STRING-INDICATOR-END (+ HOLIDAY-STRING-INDICATOR-BEGIN HOLIDAY-STRING-INDICATOR-LENGTH))

(defn hex->int [s]
  #?(:clj (Integer/parseInt s 16) :cljs (js/parseInt s 16)))

(defn parse-date [holiday-string]
  (-> (subs holiday-string HOLIDAY-STRING-TIMESTAMP-BEGIN HOLIDAY-STRING-TIMESTAMP-END)
      remove-leading-zeroes
      hex->int
      timestamp->date))

(def ^:private read-dates-single
  (fn [calendar]
    (let [lines (some-> (holiday-datelists calendar)
                        (cstr/split #"\n"))
          partition-size (if (= calendar constants/WEEKEND-FILE-NAME) WEEKEND-STRING-LENGTH HOLIDAY-STRING-LENGTH)
          holiday-strings (some->> (first lines)
                                   (partition partition-size)
                                   (map cstr/join))]
      (when holiday-strings
        (map parse-date holiday-strings)))))

(def ^:private read-dates-multi
  (fn [calendars]
    (->> calendars
         (keep read-dates-single)
         flatten
         sort
         dedupe)))

(defn read-dates [cal-or-cals]
  (if (string? cal-or-cals)
    (read-dates-single cal-or-cals)
    (read-dates-multi cal-or-cals)))

(defn parse-date-with-holiday [holiday-string holiday-names]
  {:date (-> (subs holiday-string HOLIDAY-STRING-TIMESTAMP-BEGIN HOLIDAY-STRING-TIMESTAMP-END)
             remove-leading-zeroes
             hex->int
             timestamp->date)
   :name (->> (subs holiday-string HOLIDAY-STRING-INDICATOR-BEGIN HOLIDAY-STRING-INDICATOR-END)
              (get holiday-names))})

(defn read-calendar [calendar year]
  (let [lines (some-> (holiday-datelists calendar)
                      (cstr/split #"\n"))
        partition-size HOLIDAY-STRING-LENGTH
        holiday-strings (some->> (first lines)
                                 (partition partition-size)
                                 (map cstr/join))
        holiday-names (->> (rest lines)
                           (map (fn [s] [(subs s 0 HOLIDAY-STRING-INDICATOR-LENGTH) (subs s HOLIDAY-STRING-INDICATOR-LENGTH)]))
                           flatten
                           (apply hash-map))]
    (->> holiday-strings
         (map #(parse-date-with-holiday % holiday-names))
         (filter #(= (t/year (:date %)) (t/year year)))
         (sort-by :date))))

(defn- sign [n]
  (if (pos? n) 1 -1))

(defn- get-step [n]
  (if (zero? n)
    0
    (sign n)))

(defn is-date-in-list? [date list]
  (boolean (some #{(t/date date)} list)))

(defn- inc-unless-holiday [date non-business-days days-added n]
  (if (is-date-in-list? date non-business-days)
    days-added
    (+ days-added (sign n))))

(defn- absolute [n]
  (if pos? n (- n)))

(defn add-with-calendars [date n calendars]
  (let [non-business-days (read-dates (set (conj calendars constants/WEEKEND-FILE-NAME)))
        step (t/new-period (get-step n) :days)]
    (if (= n 0)
      (if (is-date-in-list? date non-business-days)
        (add-with-calendars date 1 calendars)
        date)
      (loop [candidate date
             days-added 0]
        (if (= (absolute n) days-added)
          candidate
          (let [new-date (t/>> candidate step)
                m (inc-unless-holiday new-date non-business-days days-added n)]
            (recur new-date m)))))))
