(ns com.piposaude.calendars.types.ddmmm
  (:require [clojure.string :as str]
            [com.piposaude.calendars.types.common :refer [holiday]])
  (:import (java.time.format DateTimeParseException)))

(defn get-holiday-ddmm [year name [day month]]
  (try
    (holiday name day month year)
    (catch DateTimeParseException e
      (if (str/includes? (.getMessage e) "not a leap year")
        nil
        (throw e)))))
