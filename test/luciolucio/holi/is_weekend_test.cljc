(ns luciolucio.holi.is-weekend-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :refer [defcalendartest]]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(defcalendartest should-default-weekend-option-to-sat-sun-when-weekend?-with-no-weekend-option
  (ct/is (= (holi/weekend? (t/date "2020-08-01")) (holi/weekend? (t/date "2020-08-01") :sat-sun))))

(defcalendartest should-identify-weekends-when-weekend?
  (ct/are [date expected-sat-sun expected-fri-sat]
          (and (= expected-sat-sun (holi/weekend? (t/date date) :sat-sun))
               (= expected-fri-sat (holi/weekend? (t/date date) :fri-sat))
               (= expected-sat-sun (holi/weekend? (t/date-time (str date "T11:11:00")) :sat-sun))
               (= expected-fri-sat (holi/weekend? (t/date-time (str date "T11:11:00")) :fri-sat)))
    "2020-07-30" false false
    "2020-07-31" false true
    "2020-08-01" true true
    "2020-08-02" true false
    "2020-08-03" false false))

(defcalendartest
  ^{:doc "This test relies on TEST-WEEKEND-SAT-SUN and TEST-WEEKEND-FRI-SAT datelists, which list weekends in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-weekend?-with-date-beyond-limit-year
  (ct/testing "date"
    (ct/testing "sat-sun"
      (ct/are [date]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date date) :sat-sun))
        "2021-01-01"
        "2019-12-31"))
    (ct/testing "fri-sat"
      (ct/are [date]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date date) :fri-sat))
        "2021-01-01"
        "2019-12-31")))
  (ct/testing "datetime"
    (ct/testing "sat-sun"
      (ct/are [date]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date-time (str date "T11:11:00")) :sat-sun))
        "2021-01-01"
        "2019-12-31"))
    (ct/testing "fri-sat"
      (ct/are [date]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date-time (str date "T11:11:00")) :fri-sat))
        "2021-01-01"
        "2019-12-31"))))
