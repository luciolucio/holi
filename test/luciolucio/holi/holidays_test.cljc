(ns luciolucio.holi.holidays-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-return-holidays-when-holidays-with-date
  (ct/are [date calendar expected]
          (= expected (holi/holidays (t/date date) calendar))
    "2020-08-01" "DAY-TWENTY-NINE" []
    "2020-07-29" "DAY-TWENTY-NINE" ["Twenty Ninth Day"]
    "2020-07-30" "DAY-THREE" []
    "2020-08-03" "DAY-THREE" ["Third Day"]))

(ct/deftest should-return-holidays-when-holidays-with-date-time
  (ct/are [date calendar expected]
          (= expected (holi/holidays (t/date-time date) calendar))
    "2020-08-01T00:00:00" "DAY-TWENTY-NINE" []
    "2020-07-29T11:11:22" "DAY-TWENTY-NINE" ["Twenty Ninth Day"]
    "2020-07-30T22:22:22" "DAY-THREE" []
    "2020-08-03T00:33:33" "DAY-THREE" ["Third Day"]))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND and DAY-THREE, which only list dates in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-holidays-with-date-beyond-limit-year
  (ct/are [date calendar]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/holidays (t/date date) calendar))
    "2021-08-03" "DAY-THREE"
    "2019-08-03" "DAY-THREE"))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND and DAY-THREE, which only list dates in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-holidays-with-date-time-beyond-limit-year
  (ct/are [date calendar]
          (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (holi/holidays (t/date-time date) calendar))
    "2021-08-03T10:10:10" "DAY-THREE"
    "2019-08-03T23:59:59" "DAY-THREE"))

(ct/deftest should-throw-when-holidays-with-inexistent-calendar-or-WEEKEND
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendars: HOL" (holi/holidays (t/date "2020-10-10") "HOL")))
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendar: WEEKEND" (holi/holidays (t/date "2020-10-10") "WEEKEND"))))
