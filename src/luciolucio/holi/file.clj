(ns ^:no-doc luciolucio.holi.file
  (:require [luciolucio.holi.common :as common]
            [luciolucio.holi.holidays :as gen]
            [luciolucio.holi.constants :as constants]
            [clojure.string :as cstr]
            [tick.core :as t]
            [clojure.java.io :as io])
  (:import (java.nio.file Paths)))

(defn format-YYYYMMDD [holiday]
  (t/format (t/formatter "yyyy-MM-dd") holiday))

(defn make-datelist-path! [filename output-path]
  (let [final-path (Paths/get output-path (into-array String [filename]))]
    (-> (.getParent final-path)
        .toString
        io/file
        .mkdirs)
    (-> final-path
        .toString
        (str constants/DATELIST-EXTENSION))))

(defn make-datelist-file-path! [root-path holiday-file output-path]
  (let [filename (Paths/get (subs holiday-file (count root-path)) (into-array String []))
        output-filename (cstr/join "" (drop-last 4 (.toString filename)))]
    (make-datelist-path! output-filename output-path)))

(defn get-holidays [root-path holiday-file year]
  (map #(format-YYYYMMDD (:date %)) (gen/holidays-for-year year root-path holiday-file)))

(defn gen-bracketed-holidays [root-path holiday-file year bracket-size]
  (let [years (range (- year bracket-size) (+ year (inc bracket-size)))]
    (sort (flatten (map #(get-holidays root-path holiday-file %) years)))))

(defn get-weekends [year]
  (let [start (t/new-date year 1 1)
        days (if (common/leap-year? year) 366 365)
        interval (iterate #(t/>> % (t/new-period 1 :days)) start)
        weekends (filter #(#{t/SATURDAY t/SUNDAY} (t/day-of-week %)) (take days interval))]
    (map format-YYYYMMDD weekends)))

(defn gen-bracketed-weekends [year bracket-size]
  (let [years (range (- year bracket-size) (+ year (inc bracket-size)))]
    (flatten (map get-weekends years))))

(defn generate-datelist! [root-path holiday-file output-path year bracket-size]
  (if (cstr/ends-with? (cstr/lower-case holiday-file) "weekend.hol")
    (throw (ex-info "WEEKEND.hol is not allowed" {:error :weekend-dot-hol-present}))
    (let [holidays (gen-bracketed-holidays root-path holiday-file year bracket-size)
          path (make-datelist-file-path! root-path holiday-file output-path)]
      (spit path (cstr/join "\n" holidays)))))

(defn generate-weekend-datelist! [output-path year bracket-size]
  (let [weekends (gen-bracketed-weekends year bracket-size)
        weekend-path (make-datelist-path! constants/WEEKEND-FILE-NAME output-path)]
    (spit weekend-path (cstr/join "\n" weekends))))
