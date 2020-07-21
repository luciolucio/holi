(ns com.piposaude.calendars.types.expressions.easter
  (:require [com.piposaude.calendars.types.common :refer [holiday]]
            [easter-day :as easter]))

(defn get-holiday-easter [year name operator operand]
  (let [{:keys [day month]} (easter/easter-sunday year)
        easter-holiday (holiday name (str day) (str month) year)]
    easter-holiday))
