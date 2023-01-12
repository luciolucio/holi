(ns ^:no-doc luciolucio.holi.types.ddmmmyyyy
  (:require [clojure.edn :as edn]
            [luciolucio.holi.types.common :as common]))

(defn get-holiday-ddmmyyyy [year name [[_ day month] [_ holiday-year] [_ exception-marker]]]
  (if (= (edn/read-string holiday-year) year)
    (common/holiday name day month year (pos? (count exception-marker)))
    nil))
