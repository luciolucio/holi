(ns luciolucio.holi.common
  (:require [instaparse.core :as insta]
            [luciolucio.holi.constants :as constants])
  (:import (java.nio.file Paths)))

(defonce parser (insta/parser (clojure.java.io/resource constants/PARSER-GRAMMAR-FILENAME)))

(defn drop-include [result]
  (let [[type & _] (first result)]
    (if (= :include type)
      (rest result)
      result)))

(defn holiday-was-included? [result]
  (let [[type & _] (first result)]
    (= :include type)))

(defn included-filename [root-path included-holiday-name]
  (.toString (Paths/get root-path (into-array String [(str included-holiday-name ".hol")]))))

(defn leap-year? [year]
  (cond (zero? (mod year 400)) true
        (zero? (mod year 100)) false
        (zero? (mod year 4)) true
        :else false))
