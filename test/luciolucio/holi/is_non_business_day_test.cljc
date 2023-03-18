(ns luciolucio.holi.is-non-business-day-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-identify-non-business-days-when-non-business-day?
  (ct/are [date calendars expected-sat-sun expected-fri-sat]
          (and (= expected-sat-sun (apply holi/non-business-day? (t/date date) :sat-sun calendars))
               (= expected-sat-sun (apply holi/non-business-day? (t/date-time (str date "T11:11:00")) :sat-sun calendars))
               (= expected-fri-sat (apply holi/non-business-day? (t/date date) :fri-sat calendars))
               (= expected-fri-sat (apply holi/non-business-day? (t/date-time (str date "T11:11:00")) :fri-sat calendars)))
    "2020-07-30" [] false false
    "2020-07-31" [] false true
    "2020-08-01" [] true true
    "2020-08-02" [] true false
    "2020-08-03" [] false false
    "2020-07-29" ["DAY-THREE"] false false
    "2020-07-29" ["DAY-THREE" "DAY-TWENTY-NINE"] true true
    "2020-07-30" ["DAY-THREE"] false false
    "2020-08-01" ["DAY-THREE"] true true
    "2020-08-03" ["DAY-THREE"] true true))

(ct/deftest should-default-weekend-option-to-sat-sun-when-non-business-day?-with-no-weekend-option
  (ct/testing "date"
    (ct/testing "[date]"
      (ct/is (= (holi/non-business-day? (t/date "2020-08-02"))
                (holi/non-business-day? (t/date "2020-08-02") :sat-sun))))
    (ct/testing "[date calendar]"
      (ct/is (= (holi/non-business-day? (t/date "2020-08-02") "DAY-THREE")
                (holi/non-business-day? (t/date "2020-08-02") :sat-sun "DAY-THREE"))))
    (ct/testing "[date calendar calendar]"
      (ct/is (= (holi/non-business-day? (t/date "2020-08-02") "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/non-business-day? (t/date "2020-08-02") :sat-sun "DAY-THREE" "DAY-TWENTY-NINE")))))

  (ct/testing "datetime"
    (ct/testing "[date]"
      (ct/is (= (holi/non-business-day? (t/date-time "2020-08-02T11:11:00"))
                (holi/non-business-day? (t/date-time "2020-08-02T11:11:00") :sat-sun))))
    (ct/testing "[date calendar]"
      (ct/is (= (holi/non-business-day? (t/date-time "2020-08-02T11:11:00") "DAY-THREE")
                (holi/non-business-day? (t/date-time "2020-08-02T11:11:00") :sat-sun "DAY-THREE"))))
    (ct/testing "[date calendar calendar]"
      (ct/is (= (holi/non-business-day? (t/date-time "2020-08-02T11:11:00") "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/non-business-day? (t/date-time "2020-08-02T11:11:00") :sat-sun "DAY-THREE" "DAY-TWENTY-NINE"))))))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND and/or DAY-THREE, both of which only list dates in 2020.
          Any argument outside 2020 should raise an exception"}
  should-throw-when-non-business-day?-with-date-beyond-limit-year
  (ct/testing "date"
    (ct/testing "sat-sun"
      (ct/are [date calendars]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/non-business-day? (t/date date) :sat-sun calendars))
        "2021-01-01" []
        "2021-01-01" ["DAY-THREE"]
        "2019-12-31" []
        "2019-12-31" ["DAY-THREE"]))
    (ct/testing "fri-sat"
      (ct/are [date calendars]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/non-business-day? (t/date date) :fri-sat calendars))
        "2021-01-01" []
        "2021-01-01" ["DAY-THREE"]
        "2019-12-31" []
        "2019-12-31" ["DAY-THREE"])))
  (ct/testing "datetime"
    (ct/testing "sat-sun"
      (ct/are [date calendars]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/non-business-day? (t/date-time (str date "T11:11:00")) :sat-sun calendars))
        "2021-01-01" []
        "2021-01-01" ["DAY-THREE"]
        "2019-12-31" []
        "2019-12-31" ["DAY-THREE"]))
    (ct/testing "fri-sat"
      (ct/are [date calendars]
              (thrown-with-msg? ExceptionInfo #"Date is out of bounds" (apply holi/non-business-day? (t/date-time (str date "T11:11:00")) :fri-sat calendars))
        "2021-01-01" []
        "2021-01-01" ["DAY-THREE"]
        "2019-12-31" []
        "2019-12-31" ["DAY-THREE"]))))

(ct/deftest should-throw-when-non-business-day?-with-inexistent-calendar-or-starting-with-WEEKEND
  (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): NOT-A-CALENDAR!" (holi/non-business-day? (t/date "2020-10-10") :sat-sun "NOT-A-CALENDAR!")))
  (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): NOT-A-CALENDAR!" (holi/non-business-day? (t/date "2020-10-10") :fri-sat "NOT-A-CALENDAR!")))
  (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): WEEKEND" (holi/non-business-day? (t/date "2020-10-10") :fri-sat "WEEKEND")))
  (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): WEEKEND-SAT-SUN" (holi/non-business-day? (t/date "2020-10-10") :fri-sat "WEEKEND-SAT-SUN"))))
