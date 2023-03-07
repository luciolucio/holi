(ns luciolucio.holi.is-weekend-fri-sat-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-identify-fri-sat-weekends-when-weekend?-with-date-and-fri-sat-option
  (ct/are [date expected]
          (= expected (holi/weekend? date :fri-sat))
    (t/date "2020-07-30") false
    (t/date "2020-07-31") true
    (t/date "2020-08-01") true
    (t/date "2020-08-02") false
    (t/date "2020-08-03") false))

(ct/deftest should-identify-fri-sat-weekends-when-weekend?-with-date-time-and-fri-sat-option
  (ct/are [date-time expected]
          (= expected (holi/weekend? date-time :fri-sat))
    (t/date-time "2020-07-30T00:00:00") false
    (t/date-time "2020-07-31T00:00:00") true
    (t/date-time "2020-08-01T11:11:11") true
    (t/date-time "2020-08-02T22:22:22") false
    (t/date-time "2020-08-03T23:59:59") false))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND-FRI-SAT.datelist, which lists weekends in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-weekend?-with-date-beyond-limit-year-and-fri-sat-option
  (ct/are [date]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date date) :fri-sat))
    "2021-01-01"
    "2019-12-31"))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND-FRI-SAT.datelist, which lists weekends in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-weekend?-with-date-time-beyond-limit-year-and-fri-sat-option
  (ct/are [date]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date-time date) :fri-sat))
    "2021-01-01T00:00:00"
    "2019-12-31T23:59:59"))
