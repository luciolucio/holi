(ns com.piposaude.calendars.file
  (:require [com.piposaude.components.store.api :as store.api]
            [com.piposaude.calendars.generate :as gen]
            [clojure.string :as str]
            [tick.alpha.api :as t])
  (:import (java.nio.file Paths)))

(defn gen-store-path [holiday-file output-path]
  (let [filename (.toString (.getFileName (Paths/get holiday-file (into-array String []))))
        filename-sans-extension (str/join "" (drop-last 4 filename))]
    (.toString (Paths/get output-path (into-array String [filename-sans-extension])))))

(defn format-holiday [holiday]
  (t/format (tick.format/formatter "yyyyMMdd") (:date holiday)))

(defn get-holidays [holiday-file year]
  (map format-holiday (gen/holidays-for-year year holiday-file)))

(defn gen-bracketed-holidays [holiday-file year bracket-size]
  (let [years (range (- year bracket-size) (+ year (inc bracket-size)))
        holidays (flatten (map (partial get-holidays holiday-file) years))]
    (str/join "\n" holidays)))

(defn generate [store holiday-file year bracket-size output-path]
  (let [holidays (gen-bracketed-holidays holiday-file year bracket-size)
        store-path (gen-store-path holiday-file output-path)]
    (store.api/store-object! store store-path holidays)))
