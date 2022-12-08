(ns luciolucio.holi.add-test.days-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [tick.core :as t]))

(ct/deftest should-calculate-correct-date-when-add-date-with-days
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-07-29") days :days) (holi/add (t/date "2020-07-29") days :day))
    0 (t/date "2020-07-29")
    1 (t/date "2020-07-30")
    11 (t/date "2020-08-09")
    365 (t/date "2021-07-29")
    -365 (t/date "2019-07-30")
    -4 (t/date "2020-07-25")
    -5 (t/date "2020-07-24")))

(ct/deftest should-calculate-correct-date-when-add-date-time-with-days
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-07-29T10:15:00") days :days) (holi/add (t/date-time "2020-07-29T10:15:00") days :day))
    0 (t/date-time "2020-07-29T10:15:00")
    1 (t/date-time "2020-07-30T10:15:00")
    11 (t/date-time "2020-08-09T10:15:00")
    365 (t/date-time "2021-07-29T10:15:00")
    -365 (t/date-time "2019-07-30T10:15:00")
    -4 (t/date-time "2020-07-25T10:15:00")
    -5 (t/date-time "2020-07-24T10:15:00")))
