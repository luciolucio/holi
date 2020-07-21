(ns com.piposaude.calendars.generate
  (:require [instaparse.core :as insta]
            [tick.core :as t]
            [com.piposaude.calendars.constants :refer [PARSER-GRAMMAR-FILENAME]]))

(defn holidays-for-year [year holiday-file]
  (let [parser (insta/parser (clojure.java.io/resource PARSER-GRAMMAR-FILENAME))
        result (parser (slurp holiday-file))]
    [(t/date "2020-01-01")]))
