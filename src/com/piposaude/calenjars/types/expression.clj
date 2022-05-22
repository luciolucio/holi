(ns com.piposaude.calenjars.types.expression
  (:require [com.piposaude.calenjars.types.expressions.easter :as easter]))

(defn make-holiday [year name args]
  (case (first args)
    :easter
    (easter/get-holiday-easter year name (rest args))

    nil))

(defn get-holiday-by-expression [year name args start-year end-year]
  (let [holiday (make-holiday year name args)]
    (cond
      (and (not start-year) (not end-year))
      holiday

      (and start-year (>= year start-year))
      holiday

      (and end-year (<= year end-year))
      holiday

      :else
      nil)))
