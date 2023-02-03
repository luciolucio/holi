(ns ^:no-doc luciolucio.holi.file
  (:require [luciolucio.holi.common :as common]
            [luciolucio.holi.holidays :as gen]
            [luciolucio.holi.constants :as constants]
            [clojure.string :as cstr]
            [tick.core :as t]
            [clojure.java.io :as io])
  (:import (java.nio.file Paths)))

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

(def TIMESTAMP-FORMAT "%4s")

(defn date->timestamp [date]
  (inc (.until constants/TIMESTAMP-REFERENCE-DATE (t/date date) (t/unit-map :days))))

(defn encode-holiday [holiday index-by-name]
  (let [timestamp (date->timestamp (:date holiday))
        formatted-timestamp (format TIMESTAMP-FORMAT (cstr/upper-case (Integer/toString timestamp 16)))
        zero-padded-timestamp (cstr/replace formatted-timestamp #" " "0")
        holiday-index (get index-by-name (:name holiday))]
    (str zero-padded-timestamp holiday-index)))

(defn gen-file-contents [root-path holiday-file central-year bracket-size]
  (let [years (range (- central-year bracket-size) (+ central-year (inc bracket-size)))
        holidays-by-name (->> (map #(gen/holidays-for-year % root-path holiday-file) years)
                              flatten
                              (group-by :name))
        index-by-name (-> (zipmap (keys holidays-by-name) (range))
                          (update-vals (fn [i] (format "%02X" i))))
        encoded-dates-by-name (update-vals holidays-by-name (fn [holiday] (map #(encode-holiday % index-by-name) holiday)))
        encoded-dates (-> encoded-dates-by-name vals flatten sort cstr/join)
        holiday-keys (->> (zipmap (vals index-by-name) (keys index-by-name))
                          seq
                          (map cstr/join))]
    (str encoded-dates "\n" (cstr/join "\n" holiday-keys))))

(defn get-weekends [year]
  (let [start (t/new-date year 1 1)
        days (if (common/leap-year? year) 366 365)
        interval (iterate #(t/>> % (t/new-period 1 :days)) start)
        weekends (filter #(#{t/SATURDAY t/SUNDAY} (t/day-of-week %)) (take days interval))]
    (map #(encode-holiday {:date %} nil) weekends)))

(defn gen-bracketed-weekends [year bracket-size]
  (let [years (range (- year bracket-size) (+ year (inc bracket-size)))]
    (-> (map get-weekends years) flatten sort cstr/join)))

(defn generate-datelist! [root-path holiday-file output-path central-year bracket-size]
  (if (cstr/ends-with? (cstr/lower-case holiday-file) "weekend.hol")
    (throw (ex-info "WEEKEND.hol is not allowed" {:error :weekend-dot-hol-present}))
    (let [file-contents (gen-file-contents root-path holiday-file central-year bracket-size)
          path (make-datelist-file-path! root-path holiday-file output-path)]
      (spit path file-contents))))

(defn generate-weekend-datelist! [output-path year bracket-size]
  (let [weekends (gen-bracketed-weekends year bracket-size)
        weekend-path (make-datelist-path! constants/WEEKEND-FILE-NAME output-path)]
    (spit weekend-path weekends)))
