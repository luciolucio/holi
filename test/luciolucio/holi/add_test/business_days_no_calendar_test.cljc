(ns luciolucio.holi.add-test.business-days-no-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest
  should-default-to-sat-sun-weekend-option-when-add-with-no-weekend-option
  (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days)
            (holi/add (t/date "2020-07-30") 3 :business-days :sat-sun)))
  (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-day)
            (holi/add (t/date "2020-07-30") 3 :business-day :sat-sun))))

(ct/deftest should-calculate-correct-date-when-add-date-with-business-days
  (ct/are [start-date days weekend-option expected]
          (= (t/date expected) (holi/add (t/date start-date) days :business-days weekend-option) (holi/add (t/date start-date) days :business-day weekend-option))
    "2020-07-30" 2 :sat-sun "2020-08-03"
    "2020-07-30" 1 :sat-sun "2020-07-31"
    "2020-07-30" 5 :sat-sun "2020-08-06"
    "2020-07-30" -5 :sat-sun "2020-07-23"
    "2020-07-29" 2 :fri-sat "2020-08-02"
    "2020-07-29" 1 :fri-sat "2020-07-30"
    "2020-07-29" 5 :fri-sat "2020-08-05"
    "2020-07-29" -3 :fri-sat "2020-07-26"))

(ct/deftest should-calculate-correct-date-when-add-date-time-with-business-days
  (ct/are [start-date-time days weekend-option expected]
          (= (t/date-time expected) (holi/add (t/date-time start-date-time) days :business-days weekend-option) (holi/add (t/date-time start-date-time) days :business-day weekend-option))
    "2020-07-30T11:11:00" 2 :sat-sun "2020-08-03T11:11:00"
    "2020-07-30T11:11:00" 1 :sat-sun "2020-07-31T11:11:00"
    "2020-07-30T11:11:00" 5 :sat-sun "2020-08-06T11:11:00"
    "2020-07-30T11:11:00" -5 :sat-sun "2020-07-23T11:11:00"
    "2020-07-29T11:11:00" 2 :fri-sat "2020-08-02T11:11:00"
    "2020-07-29T11:11:00" 1 :fri-sat "2020-07-30T11:11:00"
    "2020-07-29T11:11:00" 5 :fri-sat "2020-08-05T11:11:00"
    "2020-07-29T11:11:00" -3 :fri-sat "2020-07-26T11:11:00"))

(ct/deftest should-calculate-correct-date-when-add-saturday-date-with-business-days
  (ct/are [days weekend-option expected]
          (= (t/date expected) (holi/add (t/date "2020-08-01") days :business-days weekend-option))
    1 :sat-sun "2020-08-03"
    7 :sat-sun "2020-08-11"
    -1 :sat-sun "2020-07-31"
    -5 :sat-sun "2020-07-27"
    1 :fri-sat "2020-08-02"
    7 :fri-sat "2020-08-10"
    -1 :fri-sat "2020-07-30"
    -5 :fri-sat "2020-07-26"))

(ct/deftest should-calculate-correct-date-when-add-saturday-date-time-with-business-days
  (ct/are [days weekend-option expected]
          (= (t/date-time expected) (holi/add (t/date-time "2020-08-01T22:15:09") days :business-days weekend-option))
    1 :sat-sun "2020-08-03T22:15:09"
    7 :sat-sun "2020-08-11T22:15:09"
    -1 :sat-sun "2020-07-31T22:15:09"
    -5 :sat-sun "2020-07-27T22:15:09"
    1 :fri-sat "2020-08-02T22:15:09"
    7 :fri-sat "2020-08-10T22:15:09"
    -1 :fri-sat "2020-07-30T22:15:09"
    -5 :fri-sat "2020-07-26T22:15:09"))

(ct/deftest should-calculate-correct-date-when-add-sunday-date-with-business-days
  (ct/are [days weekend-option expected]
          (= (t/date expected) (holi/add (t/date "2020-08-02") days :business-days weekend-option))
    1 :sat-sun "2020-08-03"
    7 :sat-sun "2020-08-11"
    -1 :sat-sun "2020-07-31"
    -5 :sat-sun "2020-07-27"
    1 :fri-sat "2020-08-03"
    5 :fri-sat "2020-08-09"
    -1 :fri-sat "2020-07-30"
    -5 :fri-sat "2020-07-26"))

(ct/deftest should-calculate-correct-date-when-add-sunday-date-time-with-business-days
  (ct/are [days weekend-option expected]
          (= (t/date-time expected) (holi/add (t/date-time "2020-08-02T03:15") days :business-days weekend-option))
    1 :sat-sun "2020-08-03T03:15"
    7 :sat-sun "2020-08-11T03:15"
    -1 :sat-sun "2020-07-31T03:15"
    -5 :sat-sun "2020-07-27T03:15"
    1 :fri-sat "2020-08-03T03:15"
    5 :fri-sat "2020-08-09T03:15"
    -1 :fri-sat "2020-07-30T03:15"
    -5 :fri-sat "2020-07-26T03:15"))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-with-business-days
  (ct/are [start-date weekend-option expected-end-date]
          (= (t/date expected-end-date) (holi/add (t/date start-date) 0 :business-days weekend-option))
    "2020-07-31" :sat-sun "2020-07-31"
    "2020-08-01" :sat-sun "2020-08-03"
    "2020-08-02" :sat-sun "2020-08-03"
    "2020-08-03" :sat-sun "2020-08-03"
    "2020-08-04" :sat-sun "2020-08-04"
    "2020-07-30" :fri-sat "2020-07-30"
    "2020-07-31" :fri-sat "2020-08-02"
    "2020-08-01" :fri-sat "2020-08-02"
    "2020-08-02" :fri-sat "2020-08-02"
    "2020-08-03" :fri-sat "2020-08-03"))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-date-time-with-business-days
  (ct/are [start-date weekend-option expected-end-date]
          (= (t/date-time (str expected-end-date "T03:15")) (holi/add (t/date-time (str start-date "T03:15")) 0 :business-days weekend-option))
    "2020-07-31" :sat-sun "2020-07-31"
    "2020-08-01" :sat-sun "2020-08-03"
    "2020-08-02" :sat-sun "2020-08-03"
    "2020-08-03" :sat-sun "2020-08-03"
    "2020-08-04" :sat-sun "2020-08-04"
    "2020-07-30" :fri-sat "2020-07-30"
    "2020-07-31" :fri-sat "2020-08-02"
    "2020-08-01" :fri-sat "2020-08-02"
    "2020-08-02" :fri-sat "2020-08-02"
    "2020-08-03" :fri-sat "2020-08-03"))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND-SAT-SUN and TEST-WEEKEND-FRI-SAT, which list weekends in 2020.
          Any result outside 2020 should raise an exception"}
  should-throw-when-add-date-with-business-days-and-result-beyond-limit-years
  (ct/are [date n weekend-option]
          (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (t/date date) n :business-day weekend-option))
    "2020-01-01" -1 :sat-sun
    "2020-01-01" -2 :sat-sun
    "2020-12-31" 1 :sat-sun
    "2020-12-31" 11 :sat-sun
    "2020-01-05" -4 :sat-sun
    "2020-12-26" 5 :sat-sun
    "2020-01-01" -1 :fri-sat
    "2020-01-01" -2 :fri-sat
    "2020-12-31" 1 :fri-sat
    "2020-12-31" 11 :fri-sat
    "2020-01-05" -4 :fri-sat
    "2020-12-26" 6 :fri-sat))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND-SAT-SUN and TEST-WEEKEND-FRI-SAT, which list weekends in 2020.
          Any result outside 2020 should raise an exception"}
  should-throw-when-add-date-time-with-business-days-and-result-beyond-limit-years
  (ct/are [date n weekend-option]
          (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (t/date-time (str date "T03:15")) n :business-day weekend-option))
    "2020-01-01" -1 :sat-sun
    "2020-01-01" -2 :sat-sun
    "2020-12-31" 1 :sat-sun
    "2020-12-31" 11 :sat-sun
    "2020-01-05" -4 :sat-sun
    "2020-12-26" 5 :sat-sun
    "2020-01-01" -1 :fri-sat
    "2020-01-01" -2 :fri-sat
    "2020-12-31" 1 :fri-sat
    "2020-12-31" 11 :fri-sat
    "2020-01-05" -4 :fri-sat
    "2020-12-26" 6 :fri-sat))
