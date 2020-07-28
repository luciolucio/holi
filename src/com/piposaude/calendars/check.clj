(ns com.piposaude.calendars.check
  (:require [instaparse.core :as insta]
            [clojure.edn :as edn]
            [com.piposaude.calendars.constants :refer [PARSER-GRAMMAR-FILENAME]]))

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

(defn contains-bad-leap-dates [result]
  (some true? (map contains-bad-leap-date result)))

(defn contains-bad-includes [result]
  (some true? (map contains-bad-include result)))

(defn get-errors [filename]
  (let [parser (insta/parser (clojure.java.io/resource PARSER-GRAMMAR-FILENAME))
        result (parser (slurp filename))]
    (insta/get-failure result)))

(defn valid-holiday-file? [filename]
  (let [parser (insta/parser (clojure.java.io/resource PARSER-GRAMMAR-FILENAME))
        result (parser (slurp filename))]
    (and
      (not (insta/failure? result))
      (not (contains-bad-leap-dates result))
      (not (contains-bad-includes result)))))
