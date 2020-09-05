(ns com.piposaude.calenjars.file
  (:require [com.piposaude.calenjars.common :as common]
            [com.piposaude.calenjars.holidays :as gen]
            [clojure.string :as str]
            [tick.alpha.api :as t])
  (:import (java.nio.file Paths)))

(def WEEKEND-FILE-NAME "WEEKEND")

(defn format-YYYYMMDD [holiday]
  (t/format (tick.format/formatter "yyyy-MM-dd") holiday))

(defn gen-path [filename output-path]
  (.toString (Paths/get output-path (into-array String [filename]))))

(defn gen-holiday-file-path [holiday-file output-path]
  (let [filename (.toString (.getFileName (Paths/get holiday-file (into-array String []))))
        output-filename (str/join "" (drop-last 4 filename))]
    (gen-path output-filename output-path)))

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
        path (gen-holiday-file-path holiday-file output-path)]
    (spit path (str/join "\n" holidays))))

(defn generate-weekend! [output-path year bracket-size]
  (let [weekends (gen-bracketed-weekends year bracket-size)
        weekend-path (gen-path WEEKEND-FILE-NAME output-path)]
    (spit weekend-path (str/join "\n" weekends))))
