(ns com.piposaude.calenjars.types.ddmmm
  (:require [clojure.string :as str]
            [com.piposaude.calenjars.types.common :as common])
  (:import (java.time.format DateTimeParseException)))

(defn get-holiday-ddmm [year name [day month]]
  (try
    (common/holiday name day month year)
    (catch DateTimeParseException e
      (if (str/includes? (.getMessage e) "not a leap year")
        nil
        (throw e)))))
