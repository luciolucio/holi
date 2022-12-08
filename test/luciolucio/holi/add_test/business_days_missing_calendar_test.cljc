(ns luciolucio.holi.add-test.business-days-missing-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [tick.core :as t]))

(ct/deftest should-calculate-correct-date-when-add-date-with-business-days-unknown-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-07-30") days :business-days "X"))
    2 (t/date "2020-08-03")
    1 (t/date "2020-07-31")
    5 (t/date "2020-08-06")
    -5 (t/date "2020-07-23")))

(ct/deftest should-calculate-correct-date-when-add-date-time-with-business-days-unknown-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-07-30T11:11:00") days :business-days "Y"))
    2 (t/date-time "2020-08-03T11:11:00")
    1 (t/date-time "2020-07-31T11:11:00")
    5 (t/date-time "2020-08-06T11:11:00")
    -5 (t/date-time "2020-07-23T11:11:00")))

(ct/deftest should-calculate-correct-date-when-add-saturday-date-with-business-days-unknown-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-08-01") days :business-days "Z"))
    1 (t/date "2020-08-03")
    7 (t/date "2020-08-11")
    -1 (t/date "2020-07-31")
    -5 (t/date "2020-07-27")))

(ct/deftest should-calculate-correct-date-when-add-saturday-date-time-with-business-days-unknown-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-08-01T22:15:09") days :business-days "NOT-A-CALENDAR"))
    1 (t/date-time "2020-08-03T22:15:09")
    7 (t/date-time "2020-08-11T22:15:09")
    -1 (t/date-time "2020-07-31T22:15:09")
    -5 (t/date-time "2020-07-27T22:15:09")))

(ct/deftest should-calculate-correct-date-when-add-sunday-date-with-business-days-unknown-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-08-02") days :business-days "NOPE"))
    1 (t/date "2020-08-03")
    7 (t/date "2020-08-11")
    -1 (t/date "2020-07-31")
    -5 (t/date "2020-07-27")))

(ct/deftest should-calculate-correct-date-when-add-sunday-date-time-with-business-days-unknown-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-08-02T03:15") days :business-days "NOT-EVEN"))
    1 (t/date-time "2020-08-03T03:15")
    7 (t/date-time "2020-08-11T03:15")
    -1 (t/date-time "2020-07-31T03:15")
    -5 (t/date-time "2020-07-27T03:15")))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-with-business-days-unknown-calendar
  (ct/are [start-date expected-end-date]
          (= (t/date expected-end-date) (holi/add (t/date start-date) 0 :business-days "NOT-EVEN"))
    "2020-07-31" "2020-07-31"
    "2020-08-01" "2020-08-03"
    "2020-08-02" "2020-08-03"
    "2020-08-03" "2020-08-03"
    "2020-08-04" "2020-08-04"))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-date-time-with-business-days-unknown-calendar
  (ct/are [start-date expected-end-date]
          (= (t/date-time (str expected-end-date "T03:15")) (holi/add (t/date-time (str start-date "T03:15")) 0 :business-days "NOT-EVEN"))
    "2020-07-31" "2020-07-31"
    "2020-08-01" "2020-08-03"
    "2020-08-02" "2020-08-03"
    "2020-08-03" "2020-08-03"
    "2020-08-04" "2020-08-04"))
