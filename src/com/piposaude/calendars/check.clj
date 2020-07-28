(ns com.piposaude.calendars.check
  (:require [instaparse.core :as insta]
            [clojure.edn :as edn]
            [com.piposaude.calendars.constants :refer [PARSER-GRAMMAR-FILENAME]])
  (:import (java.nio.file Paths)
           (java.io FileNotFoundException)))

(defn leap-year? [[_ year-str]]
  (let [year (edn/read-string year-str)]
    (cond (zero? (mod year 400)) true
          (zero? (mod year 100)) false
          (zero? (mod year 4)) true
          :default false)))

(defn leap-day? [[_ day month]]
  (and (= "29" day)
       (= "Feb" month)))

(defn contains-bad-leap-date [holiday]
  (let [[_ _ [type & data]] holiday]
    (if (= :ddmmmyyyy type)
      (and (leap-day? (first data)) (not (leap-year? (second data))))
      false)))

(defn contains-bad-leap-dates? [result]
  (some true? (map contains-bad-leap-date result)))

(defn holiday-was-included? [result]
  (let [[type & _] (first result)]
    (= :include type)))

(defn included-filename [including-file included-holiday-name]
  (let [including-file-path (.toString (.getParent (Paths/get including-file (into-array String []))))]
    (.toString (Paths/get including-file-path (into-array String [(str included-holiday-name ".hol")])))))

(defn included-holiday-exists? [result including-file]
  (let [included-holiday-name (second (first result))
        included-filename (included-filename including-file included-holiday-name)]
    (if (holiday-was-included? result)
      (try
        (boolean (clojure.java.io/reader included-filename))
        (catch FileNotFoundException e
          false))
      true)))

(defn drop-include [result]
  (let [[type & _] (first result)]
    (if (= :include type)
      (rest result)
      result)))

(defn valid-holiday-file? [filename]
  (let [parser (insta/parser (clojure.java.io/resource PARSER-GRAMMAR-FILENAME))
        result (parser (slurp filename))]
    (and
      (not (insta/failure? result))
      (not (contains-bad-leap-dates? (drop-include result)))
      (included-holiday-exists? result filename)
      (if-not (holiday-was-included? result)
        true
        (valid-holiday-file? (included-filename filename (second (first result))))))))

(defn get-errors [filename]
  (let [parser (insta/parser (clojure.java.io/resource PARSER-GRAMMAR-FILENAME))
        result (parser (slurp filename))]
    (merge
      {:parse-errors             (insta/get-failure result)
       :contains-bad-leap-dates? (contains-bad-leap-dates? (drop-include result))
       :included-holiday-exists? (included-holiday-exists? result filename)}
      (when (holiday-was-included? result)
        {:included-holiday-valid? (valid-holiday-file? (included-filename filename (second (first result))))}))))
