(ns luciolucio.holi.core
  (:require [clojure.string :as cstr]
            [luciolucio.holi.constants :as constants]
            [tick.core :as t]
            [shadow.resource :as rc]))

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

(def holiday-strings
  ; TODO: Generate this at build time
  {"WEEKEND"          (rc/inline "calendars-generated/WEEKEND.datelist")
   "US"               (rc/inline "calendars-generated/BR.datelist")
   "GB"               (rc/inline "calendars-generated/GB.datelist")
   "BR"               (rc/inline "calendars-generated/BR.datelist")
   "brazil/sao-paulo" (rc/inline "calendars-generated/brazil/sao-paulo.datelist")})

(def read-calendar
  (memoize
   (fn [calendar]
     (let [holiday-strings (some-> (holiday-strings calendar)
                                   (cstr/split #"\n"))]
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

(defn is-date-in-list? [date list]
  (boolean (some #{(t/date date)} list)))

(defn- inc-unless-holiday [date non-business-days days-added n]
  (if (is-date-in-list? date non-business-days)
    days-added
    (+ days-added (sign n))))

(defn- absolute [n]
  (if pos? n (- n)))

(defn add-with-calendars [date n calendars]
  (let [non-business-days (read-calendars (set (conj calendars constants/WEEKEND-FILE-NAME)))
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