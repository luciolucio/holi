(ns com.piposaude.relative-date-test.months-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calenjars :refer [relative-date-add]]
            [tick.alpha.api :as t]))

(deftest should-calculate-correct-date-when-relative-date-add-date-with-months
  (are [days expected]
       (= expected (relative-date-add (t/date "2020-07-31") days :months))
    0 (t/date "2020-07-31")
    1 (t/date "2020-08-31")
    11 (t/date "2021-06-30")
    365 (t/date "2050-12-31")
    -365 (t/date "1990-02-28")
    -4 (t/date "2020-03-31")
    -5 (t/date "2020-02-29")))

(deftest should-calculate-correct-date-when-relative-date-add-date-time-with-months
  (are [days expected]
       (= expected (relative-date-add (t/date-time "2020-07-31T10:15:00") days :months))
    0 (t/date-time "2020-07-31T10:15:00")
    1 (t/date-time "2020-08-31T10:15:00")
    11 (t/date-time "2021-06-30T10:15:00")
    365 (t/date-time "2050-12-31T10:15:00")
    -365 (t/date-time "1990-02-28T10:15:00")
    -4 (t/date-time "2020-03-31T10:15:00")
    -5 (t/date-time "2020-02-29T10:15:00")))
