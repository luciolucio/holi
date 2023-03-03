(ns luciolucio.holi.is-non-business-day-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-identify-non-business-days-when-non-business-day?-with-date
  (ct/are [date calendars expected]
          (= expected (apply holi/non-business-day? date calendars))
    (t/date "2020-08-01") [] true
    (t/date "2020-08-03") [] false
    (t/date "2020-07-29") ["DAY-THREE"] false
    (t/date "2020-07-29") ["DAY-THREE" "DAY-TWENTY-NINE"] true
    (t/date "2020-07-30") ["DAY-THREE"] false
    (t/date "2020-08-01") ["DAY-THREE"] true
    (t/date "2020-08-03") ["DAY-THREE"] true))

(ct/deftest should-identify-non-business-days-when-non-business-day?-with-date-time
  (ct/are [date-time calendars expected]
          (= expected (apply holi/non-business-day? date-time calendars))
    (t/date-time "2020-08-01T00:00:00") [] true
    (t/date-time "2020-08-03T11:10:15") [] false
    (t/date-time "2020-07-29T00:11:22") ["DAY-THREE"] false
    (t/date-time "2020-07-29T00:11:22") ["DAY-THREE" "DAY-TWENTY-NINE"] true
    (t/date-time "2020-07-30T16:40") ["DAY-THREE"] false
    (t/date-time "2020-08-01T23:59:59") ["DAY-THREE"] true
    (t/date-time "2020-08-03T00:00:01") ["DAY-THREE"] true))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND and/or DAY-THREE, both of which only list dates in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-non-business-day?-with-date-beyond-limit-year
  (ct/are [date calendars]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/non-business-day? (t/date date) calendars))
    "2021-01-01" []
    "2021-01-01" ["DAY-THREE"]
    "2019-12-31" []
    "2019-12-31" ["DAY-THREE"]))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND and/or DAY-THREE, both of which only list dates in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-non-business-day?-with-date-time-beyond-limit-year
  (ct/are [date calendars]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/non-business-day? (t/date-time date) calendars))
    "2021-01-01T00:00:00" []
    "2021-01-01T00:00:00" ["DAY-THREE"]
    "2019-12-31T23:59:59" []
    "2019-12-31T23:59:59" ["DAY-THREE"]))
