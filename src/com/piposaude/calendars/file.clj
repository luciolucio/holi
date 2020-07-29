(ns com.piposaude.calendars.file
  (:require [com.piposaude.components.store.api :as store.api]
            [com.piposaude.calendars.generate :as gen]
            [clojure.string :as str]
            [tick.alpha.api :as t]
            [clojure.tools.logging :as log])
  (:import (java.nio.file Paths)))

(defn gen-store-path [holiday-file]
  (let [filename (.toString (.getFileName (Paths/get holiday-file (into-array String []))))]
    (str/join "" (drop-last 4 filename))))

(defn format-YYYYMMDD [holiday]
  (t/format (tick.format/formatter "yyyyMMdd") holiday))

(defn get-holidays [holiday-file year]
  (map #(format-YYYYMMDD (:date %)) (gen/holidays-for-year year holiday-file)))

(defn gen-bracketed-holidays [holiday-file year bracket-size]
  (let [years (range (- year bracket-size) (+ year (inc bracket-size)))
        holidays (flatten (map (partial get-holidays holiday-file) years))]
    (str/join "\n" (sort holidays))))

(defn archive-current-holiday! [store path holidays today]
  (let [existing-holidays (slurp (:input-stream (store.api/fetch-object store path)))]
    (if (= holidays existing-holidays)
      (log/info (format "Holiday %s did not change, not archiving" path))
      (store.api/move-object! store path (str path "-UNTIL-" (format-YYYYMMDD today))))))

(defn generate! [store holiday-file year bracket-size today]
  (let [holidays (gen-bracketed-holidays holiday-file year bracket-size)
        path (gen-store-path holiday-file)]
    (when (store.api/does-object-exist? store path)
      (archive-current-holiday! store path holidays today))
    (store.api/store-object! store path holidays)))
