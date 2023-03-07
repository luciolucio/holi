(ns luciolucio.holi.is-weekend-sat-sun-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-identify-sat-sun-weekends-when-weekend?-with-date
  (ct/are [date expected]
          (= expected (holi/weekend? date) (holi/weekend? date :sat-sun))
    (t/date "2020-07-31") false
    (t/date "2020-08-01") true
    (t/date "2020-08-02") true
    (t/date "2020-08-03") false))

(ct/deftest should-identify-sat-sun-weekends-when-weekend?-with-date-time
  (ct/are [date-time expected]
          (= expected (holi/weekend? date-time) (holi/weekend? date-time :sat-sun))
    (t/date-time "2020-07-31T00:00:00") false
    (t/date-time "2020-08-01T11:11:11") true
    (t/date-time "2020-08-02T22:22:22") true
    (t/date-time "2020-08-03T23:59:59") false))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND-SAT-SUN.datelist, which lists weekends in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-weekend?-with-date-beyond-limit-year
  (ct/are [date]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date date)))
    "2021-01-01"
    "2019-12-31")
  (ct/are [date]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date date) :sat-sun))
    "2021-01-01"
    "2019-12-31"))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND-SAT-SUN.datelist, which lists weekends in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-weekend?-with-date-time-beyond-limit-year
  (ct/are [date]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date-time date)))
    "2021-01-01T00:00:00"
    "2019-12-31T23:59:59")
  (ct/are [date]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/weekend? (t/date-time date) :sat-sun))
    "2021-01-01T00:00:00"
    "2019-12-31T23:59:59"))
