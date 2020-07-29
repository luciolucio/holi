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

(defn holiday-exists? [store path]
  (store.api/does-object-exist? store path))

(defn archive-current-holiday! [store path today]
  (let [archive-filename (str path "-UNTIL-" (format-YYYYMMDD today))
        archive-exists? (store.api/does-object-exist? store archive-filename)]
    (when-not archive-exists?
      (log/info (format "Archiving %s to %s" path archive-filename))
      (store.api/move-object! store path archive-filename))))

(defn holiday-changed? [store path holidays]
  (let [existing-holidays (slurp (:input-stream (store.api/fetch-object store path)))]
    (not (= holidays existing-holidays))))

(defn generate! [store holiday-file year bracket-size today]
  (let [holidays (gen-bracketed-holidays holiday-file year bracket-size)
        path (gen-store-path holiday-file)]
    (if (holiday-exists? store path)
      (if (holiday-changed? store path holidays)
        (do
          (archive-current-holiday! store path today)
          (log/info (format "Saving changed holiday %s" path))
          (store.api/store-object! store path holidays))
        (log/info (format "Holiday %s did not change, ignoring" path)))
      (do
        (log/info (format "Saving new holiday %s" path))
        (store.api/store-object! store path holidays)))))
