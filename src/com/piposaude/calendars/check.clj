(ns com.piposaude.calendars.check
  (:require [instaparse.core :as insta]))

(defn valid-holiday-file? [filename]
  (let [parser (insta/parser (clojure.java.io/resource "holidays.bnf"))]
    (not (insta/failure? (parser (slurp filename))))))
