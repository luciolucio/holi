(ns com.piposaude.calenjars.types.rule
  (:require [com.piposaude.calenjars.types.rules.nth-day-of-week :as nth-day-of-week]))

(defn get-holiday-rule [year name args]
  (condp = (first args)
    :nth-day-of-week
    (nth-day-of-week/get-holiday-nth-day-of-week year name (rest args))

    nil))
