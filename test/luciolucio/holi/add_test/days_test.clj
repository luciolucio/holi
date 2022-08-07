(ns luciolucio.holi.add-test.days-test
  (:require [clojure.test :refer :all]
            [luciolucio.holi.core :as holi]
            [tick.alpha.api :as t]))

(deftest should-calculate-correct-date-when-add-date-with-days
  (are [days expected]
       (= expected (holi/add (t/date "2020-07-29") days :days))
    0 (t/date "2020-07-29")
    1 (t/date "2020-07-30")
    11 (t/date "2020-08-09")
    365 (t/date "2021-07-29")
    -365 (t/date "2019-07-30")
    -4 (t/date "2020-07-25")
    -5 (t/date "2020-07-24")))

(deftest should-calculate-correct-date-when-add-date-time-with-days
  (are [days expected]
       (= expected (holi/add (t/date-time "2020-07-29T10:15:00") days :days))
    0 (t/date-time "2020-07-29T10:15:00")
    1 (t/date-time "2020-07-30T10:15:00")
    11 (t/date-time "2020-08-09T10:15:00")
    365 (t/date-time "2021-07-29T10:15:00")
    -365 (t/date-time "2019-07-30T10:15:00")
    -4 (t/date-time "2020-07-25T10:15:00")
    -5 (t/date-time "2020-07-24T10:15:00")))
