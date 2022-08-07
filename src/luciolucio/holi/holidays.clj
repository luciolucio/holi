(ns luciolucio.holi.holidays
  (:require [clojure.edn :as edn]
            [instaparse.core :as insta]
            [luciolucio.holi.common :as common]
            [luciolucio.holi.check :as check]
            [luciolucio.holi.types.ddmmm :as ddmm]
            [luciolucio.holi.types.ddmmmyyyy :as ddmmyyyy]
            [luciolucio.holi.types.expression :as expression]
            [luciolucio.holi.types.nth-day-of-week :as nth-day-of-week]
            [luciolucio.holi.constants :as constants]))

(defn valid-year? [year]
  (and (integer? year) (<= constants/MIN-YEAR year constants/MAX-YEAR)))

(defmulti opt-value (fn [opt-kw _args] opt-kw))

(defmethod opt-value :observation-rule [_ args] (keyword (first args)))
(defmethod opt-value :start-year [_ args] (edn/read-string (first args)))
(defmethod opt-value :end-year [_ args] (edn/read-string (first args)))

(defn parse-holiday-opts [[_ & opts]]
  (reduce (fn [holiday-opts [opt-kw & args]]
            (assoc holiday-opts opt-kw (opt-value opt-kw args))) {} opts))

(defn get-holiday [year [_ name [type & args] opts]]
  (let [{:keys [observation-rule start-year end-year]} (parse-holiday-opts opts)]
    (condp = type
      :ddmmm (ddmm/get-holiday-ddmm year name args observation-rule start-year end-year)
      :ddmmmyyyy (ddmmyyyy/get-holiday-ddmmyyyy year name args)
      :nth-day-of-week (nth-day-of-week/get-holiday-nth-day-of-week year name args start-year end-year)
      :expression (expression/get-holiday-by-expression year name (first args) observation-rule start-year end-year)
      nil)))

(defn remove-exceptions [holidays]
  (let [exception-dates (set (map :date (filter :exception? holidays)))]
    (remove #(some #{(:date %)} exception-dates) holidays)))

(defn- holidays-for-year-with-exception-key [year holiday-file]
  (cond
    (not (valid-year? year))
    (throw (ex-info (str "Invalid year: " year) {:year year}))

    (not (check/valid-holiday-file? holiday-file))
    (throw (ex-info (str "Invalid holiday file: " holiday-file) {:holiday-file holiday-file :errors (check/get-errors holiday-file)}))

    :else
    (let [parser (insta/parser (clojure.java.io/resource constants/PARSER-GRAMMAR-FILENAME))
          result (parser (slurp holiday-file))
          holidays (conj
                    (keep #(get-holiday year %) (common/drop-include result))
                    (when (common/holiday-was-included? result)
                      (holidays-for-year-with-exception-key year (common/included-filename holiday-file (second (first result))))))]
      (-> (remove nil? holidays)
          flatten
          remove-exceptions))))

(defn holidays-for-year [year holiday-file]
  (map #(select-keys % [:name :date]) (holidays-for-year-with-exception-key year holiday-file)))
