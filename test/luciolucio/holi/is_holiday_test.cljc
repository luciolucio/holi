(ns luciolucio.holi.is-holiday-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-identify-holidays-when-holiday?-with-date
  (ct/are [date calendar expected]
          (= expected (holi/holiday? date calendar))
    (t/date "2020-08-01") "X" false
    (t/date "2020-08-03") "X" false
    (t/date "2020-07-29") "DAY-TWENTY-NINE" true
    (t/date "2020-07-30") "DAY-THREE" false
    (t/date "2020-08-01") "DAY-THREE" false
    (t/date "2020-08-03") "DAY-THREE" true))

(ct/deftest should-identify-holidays-when-holiday?-with-date-time
  (ct/are [date calendar expected]
          (= expected (holi/holiday? date calendar))
    (t/date-time "2020-08-01T00:00:00") "X" false
    (t/date-time "2020-08-03T11:11:11") "X" false
    (t/date-time "2020-07-29T22:22:22") "DAY-TWENTY-NINE" true
    (t/date-time "2020-07-30T23:59:59") "DAY-THREE" false
    (t/date-time "2020-08-01T16:40:40") "DAY-THREE" false
    (t/date-time "2020-08-03T09:09:09") "DAY-THREE" true))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND and DAY-THREE, which only list dates in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-holiday?-with-date-beyond-limit-year
  (ct/are [date calendar]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/holiday? (t/date date) calendar))
    "2021-08-03" "DAY-THREE"
    "2019-08-03" "DAY-THREE"))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND and DAY-THREE, which only list dates in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-holiday?-with-date-time-beyond-limit-year
  (ct/are [date calendar]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/holiday? (t/date-time date) calendar))
    "2021-08-03T10:10:10" "DAY-THREE"
    "2019-08-03T23:59:59" "DAY-THREE"))
