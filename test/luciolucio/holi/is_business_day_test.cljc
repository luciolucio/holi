(ns luciolucio.holi.is-business-day-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-identify-business-days-when-business-days?-with-date
  (ct/are [date calendars expected-sat-sun expected-fri-sat]
          (and (= expected-sat-sun (apply holi/business-day? (t/date date) :sat-sun calendars))
               (= expected-sat-sun (apply holi/business-day? (t/date-time (str date "T11:11:00")) :sat-sun calendars))
               (= expected-fri-sat (apply holi/business-day? (t/date date) :fri-sat calendars))
               (= expected-fri-sat (apply holi/business-day? (t/date-time (str date "T11:11:00")) :fri-sat calendars)))
    "2020-07-30" [] true true
    "2020-07-31" [] true false
    "2020-08-01" [] false false
    "2020-08-02" [] false true
    "2020-08-03" [] true true
    "2020-07-29" ["DAY-THREE"] true true
    "2020-07-29" ["DAY-THREE" "DAY-TWENTY-NINE"] false false
    "2020-07-30" ["DAY-THREE"] true true
    "2020-08-01" ["DAY-THREE"] false false
    "2020-08-03" ["DAY-THREE"] false false))

(ct/deftest should-default-weekend-option-to-sat-sun-when-business-day?-with-no-weekend-option
  (ct/testing "date"
    (ct/testing "[date]"
      (ct/is (= (holi/business-day? (t/date "2020-08-02"))
                (holi/business-day? (t/date "2020-08-02") :sat-sun))))
    (ct/testing "[date calendar]"
      (ct/is (= (holi/business-day? (t/date "2020-08-02") "DAY-THREE")
                (holi/business-day? (t/date "2020-08-02") :sat-sun "DAY-THREE"))))
    (ct/testing "[date calendar calendar]"
      (ct/is (= (holi/business-day? (t/date "2020-08-02") "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/business-day? (t/date "2020-08-02") :sat-sun "DAY-THREE" "DAY-TWENTY-NINE")))))

  (ct/testing "datetime"
    (ct/testing "[date]"
      (ct/is (= (holi/business-day? (t/date-time "2020-08-02T11:11:00"))
                (holi/business-day? (t/date-time "2020-08-02T11:11:00") :sat-sun))))
    (ct/testing "[date calendar]"
      (ct/is (= (holi/business-day? (t/date-time "2020-08-02T11:11:00") "DAY-THREE")
                (holi/business-day? (t/date-time "2020-08-02T11:11:00") :sat-sun "DAY-THREE"))))
    (ct/testing "[date calendar calendar]"
      (ct/is (= (holi/business-day? (t/date-time "2020-08-02T11:11:00") "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/business-day? (t/date-time "2020-08-02T11:11:00") :sat-sun "DAY-THREE" "DAY-TWENTY-NINE"))))))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND and/or DAY-THREE, both of which only list dates in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-business-day?-with-date-beyond-limit-year
  (ct/testing "date"
    (ct/testing "sat-sun"
      (ct/are [date calendars]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/business-day? (t/date date) :sat-sun calendars))
        "2021-01-01" []
        "2021-01-01" ["DAY-THREE"]
        "2019-12-31" []
        "2019-12-31" ["DAY-THREE"]))
    (ct/testing "fri-sat"
      (ct/are [date calendars]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/business-day? (t/date date) :fri-sat calendars))
        "2021-01-01" []
        "2021-01-01" ["DAY-THREE"]
        "2019-12-31" []
        "2019-12-31" ["DAY-THREE"])))
  (ct/testing "datetime"
    (ct/testing "sat-sun"
      (ct/are [date calendars]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/business-day? (t/date-time (str date "T11:11:00")) :sat-sun calendars))
        "2021-01-01" []
        "2021-01-01" ["DAY-THREE"]
        "2019-12-31" []
        "2019-12-31" ["DAY-THREE"]))
    (ct/testing "fri-sat"
      (ct/are [date calendars]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/business-day? (t/date-time (str date "T11:11:00")) :fri-sat calendars))
        "2021-01-01" []
        "2021-01-01" ["DAY-THREE"]
        "2019-12-31" []
        "2019-12-31" ["DAY-THREE"]))))

(ct/deftest should-throw-when-business-day?-with-inexistent-calendar
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendars: NOT-A-CALENDAR!" (holi/business-day? (t/date "2020-10-10") :sat-sun "NOT-A-CALENDAR!")))
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendars: NOT-A-CALENDAR!" (holi/business-day? (t/date "2020-10-10") :fri-sat "NOT-A-CALENDAR!"))))
