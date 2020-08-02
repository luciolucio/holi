(ns com.piposaude.relative-date-test.days-test
  (:require [clojure.test :refer :all]
            [com.piposaude.relative-date-add :refer [relative-date-add]]
            [tick.core :as t]))

(deftest should-calculate-correct-date-when-relative-date-add-date-with-days
  (are [days expected]
    (= expected (relative-date-add (t/date "2020-07-29") days :days))
    0 (t/date "2020-07-29")
    1 (t/date "2020-07-30")
    11 (t/date "2020-08-09")
    365 (t/date "2021-07-29")
    -365 (t/date "2019-07-30")
    -4 (t/date "2020-07-25")
    -5 (t/date "2020-07-24")))

(deftest should-calculate-correct-date-when-relative-date-add-date-time-with-days
  (are [days expected]
    (= expected (relative-date-add (t/date-time "2020-07-29T10:15:00") days :days))
    0 (t/date-time "2020-07-29T10:15:00")
    1 (t/date-time "2020-07-30T10:15:00")
    11 (t/date-time "2020-08-09T10:15:00")
    365 (t/date-time "2021-07-29T10:15:00")
    -365 (t/date-time "2019-07-30T10:15:00")
    -4 (t/date-time "2020-07-25T10:15:00")
    -5 (t/date-time "2020-07-24T10:15:00")))
