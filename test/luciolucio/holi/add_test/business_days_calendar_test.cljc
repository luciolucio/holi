(ns luciolucio.holi.add-test.business-days-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-calculate-correct-date-when-add-date-with-business-days-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-07-30") days :business-days "DAY-THREE"))
    2 (t/date "2020-08-04")
    1 (t/date "2020-07-31")
    5 (t/date "2020-08-07")
    -5 (t/date "2020-07-23")))

(ct/deftest should-calculate-correct-date-when-add-date-time-with-business-days-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-07-30T11:11:00") days :business-days "DAY-THREE"))
    2 (t/date-time "2020-08-04T11:11:00")
    1 (t/date-time "2020-07-31T11:11:00")
    5 (t/date-time "2020-08-07T11:11:00")
    -5 (t/date-time "2020-07-23T11:11:00")))

(ct/deftest should-calculate-correct-date-when-add-saturday-date-with-business-days-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-08-01") days :business-days "DAY-THREE"))
    1 (t/date "2020-08-04")
    7 (t/date "2020-08-12")
    -1 (t/date "2020-07-31")
    -5 (t/date "2020-07-27")))

(ct/deftest should-calculate-correct-date-when-add-saturday-date-time-with-business-days-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-08-01T22:15:09") days :business-days "DAY-THREE"))
    1 (t/date-time "2020-08-04T22:15:09")
    7 (t/date-time "2020-08-12T22:15:09")
    -1 (t/date-time "2020-07-31T22:15:09")
    -5 (t/date-time "2020-07-27T22:15:09")))

(ct/deftest should-calculate-correct-date-when-add-sunday-date-with-business-days-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-08-02") days :business-days "DAY-THREE"))
    1 (t/date "2020-08-04")
    7 (t/date "2020-08-12")
    -1 (t/date "2020-07-31")
    -5 (t/date "2020-07-27")))

(ct/deftest should-calculate-correct-date-when-add-sunday-date-time-with-business-days-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-08-02T03:15") days :business-days "DAY-THREE"))
    1 (t/date-time "2020-08-04T03:15")
    7 (t/date-time "2020-08-12T03:15")
    -1 (t/date-time "2020-07-31T03:15")
    -5 (t/date-time "2020-07-27T03:15")))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-with-business-days-calendar
  (ct/are [start-date expected-end-date]
          (= (t/date expected-end-date) (holi/add (t/date start-date) 0 :business-days "DAY-THREE"))
    "2020-07-31" "2020-07-31"
    "2020-08-01" "2020-08-04"
    "2020-08-02" "2020-08-04"
    "2020-08-03" "2020-08-04"
    "2020-08-04" "2020-08-04"))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-date-time-with-business-days-calendar
  (ct/are [start-date expected-end-date]
          (= (t/date-time (str expected-end-date "T03:15")) (holi/add (t/date-time (str start-date "T03:15")) 0 :business-days "DAY-THREE"))
    "2020-07-31" "2020-07-31"
    "2020-08-01" "2020-08-04"
    "2020-08-02" "2020-08-04"
    "2020-08-03" "2020-08-04"
    "2020-08-04" "2020-08-04"))

(ct/deftest should-calculate-correct-date-when-add-with-holiday-coinciding-with-weekend
  (ct/is (= (t/date "2020-01-22") (holi/add (t/date "2020-01-18") 3 :business-days "HOLIDAY-ON-WEEKEND")))
  (ct/is (= (t/date-time "2020-01-22T03:15") (holi/add (t/date-time "2020-01-18T03:15") 3 :business-days "HOLIDAY-ON-WEEKEND"))))

(ct/deftest should-throw-exception-when-add-date-with-business-days-calendar-and-result-beyond-limit-years
  "This test relies on DAY-THREE.datelist, which lists 3Aug20 as a holiday.
  Any result outside 2020 should raise an exception"
  (ct/are [date n]
          (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (t/date date) n :business-day "DAY-THREE"))
    "2020-08-02" 109 ; Would be 31Dec20 without the holiday, but will be out of bounds with it
    "2020-01-01" -1
    "2020-01-01" -2
    "2020-12-31" 1
    "2020-12-31" 11
    "2020-01-05" -4
    "2020-12-26" 5))

(ct/deftest should-throw-exception-when-add-date-time-with-business-days-calendar-and-result-beyond-limit-years
  "This test relies on DAY-THREE.datelist, which lists 3Aug20 as a holiday.
  Any result outside 2020 should raise an exception"
  (ct/are [date n]
          (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (t/date-time (str date "T03:15")) n :business-day "DAY-THREE"))
    "2020-08-02" 109 ; Would be 31Dec20 without the holiday, but will be out of bounds with it
    "2020-01-01" -1
    "2020-01-01" -2
    "2020-12-31" 1
    "2020-12-31" 11
    "2020-01-05" -4
    "2020-12-26" 5))
