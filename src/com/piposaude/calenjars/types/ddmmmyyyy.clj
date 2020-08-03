(ns com.piposaude.calenjars.types.ddmmmyyyy
  (:require [clojure.edn :as edn]
            [com.piposaude.calenjars.types.common :refer [holiday]]))

(defn get-holiday-ddmmyyyy [year name [[_ day month] [_ holiday-year] [_ exception-marker]]]
  (if (= (edn/read-string holiday-year) year)
    (holiday name day month year (pos? (count exception-marker)))
    nil))
