(ns com.piposaude.calendars.file
  (:require [com.piposaude.calendars.common :as common]
            [com.piposaude.calendars.holidays :as gen]
            [clojure.string :as str]
            [tick.alpha.api :as t])
  (:import (java.nio.file Paths)))

(defn format-YYYYMMDD [holiday]
  (t/format (tick.format/formatter "yyyy-MM-dd") holiday))

(defn gen-path [holiday-file output-path]
  (let [filename (.toString (.getFileName (Paths/get holiday-file (into-array String []))))
        output-filename (str/join "" (drop-last 4 filename))]
    (.toString (Paths/get output-path (into-array String [output-filename])))))

(defn get-holidays [holiday-file year]
  (map #(format-YYYYMMDD (:date %)) (gen/holidays-for-year year holiday-file)))

(defn gen-bracketed-holidays [holiday-file year bracket-size]
  (let [years (range (- year bracket-size) (+ year (inc bracket-size)))]
    (sort (flatten (map (partial get-holidays holiday-file) years)))))

(defn get-weekends [year]
  (let [start (t/new-date year 1 1)
        days (if (common/leap-year? year) 366 365)
        interval (iterate #(t/+ % (t/new-period 1 :days)) start)
        weekends (filter #(#{t/SATURDAY t/SUNDAY} (t/day-of-week %)) (take days interval))]
    (map format-YYYYMMDD weekends)))

(defn gen-bracketed-weekends [year bracket-size]
  (let [years (range (- year bracket-size) (+ year (inc bracket-size)))]
    (flatten (map get-weekends years))))

(defn generate! [holiday-file output-path year bracket-size]
  (let [holidays (gen-bracketed-holidays holiday-file year bracket-size)
        weekends (gen-bracketed-weekends year bracket-size)
        path (gen-path holiday-file output-path)
        weekend-path (gen-path "WEEKEND.hol" output-path)]
    (spit path (str/join "\n" holidays))
    (spit weekend-path (str/join "\n" weekends))))
