(ns ^:no-doc luciolucio.holi.types.expressions.easter
  (:require [luciolucio.holi.types.common :as common]
            [easter-day :as easter]
            [tick.core :as t]
            [clojure.edn :as edn]))

(defn get-holiday-easter [year name [_ operator operand-str]]
  (let [{:keys [day month]} (easter/easter-sunday year)
        easter-holiday (common/holiday name (str day) (str month) year)
        operator-fn (condp = operator
                      "+" t/>>
                      "-" t/<<)
        operand (edn/read-string operand-str)]
    (update easter-holiday :date #(operator-fn % (t/new-period operand :days)))))
