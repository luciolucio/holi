(ns com.piposaude.calendars.file
  (:require [com.piposaude.calendars.generate :as gen]
            [clojure.string :as str]
            [tick.alpha.api :as t])
  (:import (java.nio.file Paths)))

(defn gen-path [holiday-file output-path]
  (let [filename (.toString (.getFileName (Paths/get holiday-file (into-array String []))))
        output-filename (str/join "" (drop-last 4 filename))]
    (.toString (Paths/get output-path (into-array String [output-filename])))))

(defn format-YYYYMMDD [holiday]
  (t/format (tick.format/formatter "yyyyMMdd") holiday))

(defn get-holidays [holiday-file year]
  (map #(format-YYYYMMDD (:date %)) (gen/holidays-for-year year holiday-file)))

(defn gen-bracketed-holidays [holiday-file year bracket-size]
  (let [years (range (- year bracket-size) (+ year (inc bracket-size)))
        holidays (flatten (map (partial get-holidays holiday-file) years))]
    (str/join "\n" (sort holidays))))

(defn generate! [holiday-file output-path year bracket-size]
  (let [holidays (gen-bracketed-holidays holiday-file year bracket-size)
        path (gen-path holiday-file output-path)]
    (spit path holidays)))
