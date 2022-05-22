(ns com.piposaude.calenjars.types.expression
  (:require [com.piposaude.calenjars.types.expressions.easter :as easter]))

(defn get-holiday-by-expression [year name args]
  (condp = (first args)
    :easter
    (easter/get-holiday-easter year name (rest args))

    nil))
