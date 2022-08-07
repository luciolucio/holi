(ns luciolucio.holi.add-test.weeks-test
  (:require [clojure.test :refer :all]
            [luciolucio.holi :as holi]
            [tick.alpha.api :as t]))

(deftest should-calculate-correct-date-when-add-date-with-weeks
  (are [days expected]
       (= expected (holi/add (t/date "2020-07-29") days :weeks) (holi/add (t/date "2020-07-29") days :week))
    0 (t/date "2020-07-29")
    1 (t/date "2020-08-05")
    11 (t/date "2020-10-14")
    365 (t/date "2027-07-28")
    -365 (t/date "2013-07-31")
    -4 (t/date "2020-07-01")
    -5 (t/date "2020-06-24")))

(deftest should-calculate-correct-date-when-add-date-time-with-weeks
  (are [days expected]
       (= expected (holi/add (t/date-time "2020-07-29T10:15:00") days :weeks) (holi/add (t/date-time "2020-07-29T10:15:00") days :week))
    0 (t/date-time "2020-07-29T10:15:00")
    1 (t/date-time "2020-08-05T10:15:00")
    11 (t/date-time "2020-10-14T10:15:00")
    365 (t/date-time "2027-07-28T10:15:00")
    -365 (t/date-time "2013-07-31T10:15:00")
    -4 (t/date-time "2020-07-01T10:15:00")
    -5 (t/date-time "2020-06-24T10:15:00")))
