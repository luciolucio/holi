(ns com.piposaude.calenjars.types.expression
  (:require [com.piposaude.calenjars.types.expressions.easter :as easter]
            [tick.alpha.api :as t]))

(defn make-holiday [year name args]
  (case (first args)
    :easter
    (easter/get-holiday-easter year name (rest args))

    nil))

(defn get-holiday-by-expression [year name args observation-rule start-year end-year]
  (let [holiday (make-holiday year name args)]
    (cond
      (and start-year (< year start-year))
      nil

      (and end-year (> year end-year))
      nil

      (and (= observation-rule :observed) (= t/SATURDAY (t/day-of-week (:date holiday))))
      (update holiday :date #(t/- % (t/new-period 1 :days)))

      (and (= observation-rule :observed) (= t/SUNDAY (t/day-of-week (:date holiday))))
      (update holiday :date #(t/+ % (t/new-period 1 :days)))

      (and (= observation-rule :observed-monday-tuesday) (contains? #{t/SATURDAY t/SUNDAY} (t/day-of-week (:date holiday))))
      (update holiday :date #(t/+ % (t/new-period 2 :days)))

      :else
      holiday)))
