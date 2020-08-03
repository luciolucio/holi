(ns com.piposaude.calenjars.types.expressions.easter
  (:require [com.piposaude.calenjars.types.common :refer [holiday]]
            [easter-day :as easter]
            [tick.core :as t]
            [clojure.edn :as edn]))

(defn get-holiday-easter [year name operator operand-str]
  (let [{:keys [day month]} (easter/easter-sunday year)
        easter-holiday (holiday name (str day) (str month) year)
        operator-fn (condp = operator
                      "+" t/+
                      "-" t/-)
        operand (edn/read-string operand-str)]
    (update easter-holiday :date #(operator-fn % (t/new-period operand :days)))))
