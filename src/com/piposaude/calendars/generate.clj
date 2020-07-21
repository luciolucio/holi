(ns com.piposaude.calendars.generate
  (:require [instaparse.core :as insta]
            [com.piposaude.calendars.check :as check]
            [com.piposaude.calendars.types.ddmmm :as ddmm]
            [com.piposaude.calendars.types.ddmmmyyyy :as ddmmyyyy]
            [com.piposaude.calendars.types.expression :as expression]
            [com.piposaude.calendars.constants :refer :all]))

(defn valid-year? [year]
  (and (int? year) (<= MIN-YEAR year MAX-YEAR)))

(defn get-holiday [year [_ name [type & args]]]
  (condp = type
    :ddmmm (ddmm/get-holiday-ddmm year name args)
    :ddmmmyyyy (ddmmyyyy/get-holiday-ddmmyyyy year name args)
    :expression (expression/get-holiday-expression year name args)
    nil))

(defn holidays-for-year [year holiday-file]
  (cond
    (not (valid-year? year))
    (throw (ex-info "Invalid year" {:year year}))

    (not (check/valid-holiday-file? holiday-file))
    (throw (ex-info "Invalid holiday file" {:holiday-file holiday-file}))

    :default
    (let [parser (insta/parser (clojure.java.io/resource PARSER-GRAMMAR-FILENAME))
          result (parser (slurp holiday-file))]
      (keep (partial get-holiday year) result))))
