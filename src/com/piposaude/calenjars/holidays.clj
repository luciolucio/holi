(ns com.piposaude.calenjars.holidays
  (:require [instaparse.core :as insta]
            [com.piposaude.calenjars.common :as common]
            [com.piposaude.calenjars.check :as check]
            [com.piposaude.calenjars.types.ddmmm :as ddmm]
            [com.piposaude.calenjars.types.ddmmmyyyy :as ddmmyyyy]
            [com.piposaude.calenjars.types.expression :as expression]
            [com.piposaude.calenjars.constants :refer :all]))

(defn valid-year? [year]
  (and (integer? year) (<= MIN-YEAR year MAX-YEAR)))

(defn get-holiday [year [_ name [type & args]]]
  (condp = type
    :ddmmm (ddmm/get-holiday-ddmm year name args)
    :ddmmmyyyy (ddmmyyyy/get-holiday-ddmmyyyy year name args)
    :expression (expression/get-holiday-expression year name args)
    nil))

(defn remove-exceptions [holidays]
  (let [exception-dates (set (map :date (filter :exception? holidays)))]
    (remove #(some #{(:date %)} exception-dates) holidays)))

(defn- holidays-for-year-with-exception-key [year holiday-file]
  (cond
    (not (valid-year? year))
    (throw (ex-info (str "Invalid year: " year) {:year year}))

    (not (check/valid-holiday-file? holiday-file))
    (throw (ex-info (str "Invalid holiday file: " holiday-file) {:holiday-file holiday-file :errors (check/get-errors holiday-file)}))

    :default
    (let [parser (insta/parser (clojure.java.io/resource PARSER-GRAMMAR-FILENAME))
          result (parser (slurp holiday-file))
          holidays (conj
                    (keep (partial get-holiday year) (common/drop-include result))
                    (when (common/holiday-was-included? result)
                      (holidays-for-year-with-exception-key year (common/included-filename holiday-file (second (first result))))))]
      (-> (remove nil? holidays)
          flatten
          remove-exceptions))))

(defn holidays-for-year [year holiday-file]
  (map #(select-keys % [:name :date]) (holidays-for-year-with-exception-key year holiday-file)))
