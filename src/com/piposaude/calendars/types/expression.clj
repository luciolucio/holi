(ns com.piposaude.calendars.types.expression
  (:require [com.piposaude.calendars.types.expressions.easter :as easter]))

(defn get-holiday-expression [year name [expression operator operand]]
  (condp = expression
    "E" (easter/get-holiday-easter year name operator operand)
    nil))
