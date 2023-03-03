(ns luciolucio.holi.list-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-list-the-correct-holidays-for-calendar-and-year-when-list-holidays
  (ct/are [year expected]
          (= expected (holi/list-holidays year "TEST-US"))
    2021 [{:date (t/date "2021-01-01") :name "New Year's Day"}
          {:date (t/date "2021-01-18") :name "Martin Luther King Jr. Day"}
          {:date (t/date "2021-02-15") :name "President's Day"}
          {:date (t/date "2021-05-31") :name "Memorial Day"}
          {:date (t/date "2021-06-18") :name "Juneteenth"}
          {:date (t/date "2021-07-05") :name "Independence Day"}
          {:date (t/date "2021-09-06") :name "Labor Day"}
          {:date (t/date "2021-10-11") :name "Columbus Day"}
          {:date (t/date "2021-11-11") :name "Veterans Day"}
          {:date (t/date "2021-11-25") :name "Thanksgiving"}
          {:date (t/date "2021-12-24") :name "Christmas"}
          {:date (t/date "2021-12-31") :name "New Year's Day"}]

    2022 [{:date (t/date "2022-01-17") :name "Martin Luther King Jr. Day"}
          {:date (t/date "2022-02-21") :name "President's Day"}
          {:date (t/date "2022-05-30") :name "Memorial Day"}
          {:date (t/date "2022-06-20") :name "Juneteenth"}
          {:date (t/date "2022-07-04") :name "Independence Day"}
          {:date (t/date "2022-09-05") :name "Labor Day"}
          {:date (t/date "2022-10-10") :name "Columbus Day"}
          {:date (t/date "2022-11-11") :name "Veterans Day"}
          {:date (t/date "2022-11-24") :name "Thanksgiving"}
          {:date (t/date "2022-12-26") :name "Christmas"}]

    2023 [{:date (t/date "2023-01-02") :name "New Year's Day"}
          {:date (t/date "2023-01-16") :name "Martin Luther King Jr. Day"}
          {:date (t/date "2023-02-20") :name "President's Day"}
          {:date (t/date "2023-05-29") :name "Memorial Day"}
          {:date (t/date "2023-06-19") :name "Juneteenth"}
          {:date (t/date "2023-07-04") :name "Independence Day"}
          {:date (t/date "2023-09-04") :name "Labor Day"}
          {:date (t/date "2023-10-09") :name "Columbus Day"}
          {:date (t/date "2023-11-10") :name "Veterans Day"}
          {:date (t/date "2023-11-23") :name "Thanksgiving"}
          {:date (t/date "2023-12-25") :name "Christmas"}]

    2024 [{:date (t/date "2024-01-01") :name "New Year's Day"}
          {:date (t/date "2024-01-15") :name "Martin Luther King Jr. Day"}
          {:date (t/date "2024-02-19") :name "President's Day"}
          {:date (t/date "2024-05-27") :name "Memorial Day"}
          {:date (t/date "2024-06-19") :name "Juneteenth"}
          {:date (t/date "2024-07-04") :name "Independence Day"}
          {:date (t/date "2024-09-02") :name "Labor Day"}
          {:date (t/date "2024-10-14") :name "Columbus Day"}
          {:date (t/date "2024-11-11") :name "Veterans Day"}
          {:date (t/date "2024-11-28") :name "Thanksgiving"}
          {:date (t/date "2024-12-25") :name "Christmas"}]))

(ct/deftest should-throw-when-list-holidays-with-year-beyond-limits
  (ct/is (thrown-with-msg? ExceptionInfo #"Year is out of bounds" (holi/list-holidays 1942 "TEST-US")))
  (ct/is (thrown-with-msg? ExceptionInfo #"Year is out of bounds" (holi/list-holidays 2104 "TEST-US"))))

(ct/deftest
  ^{:doc "These should say 'No such calendars' before
          checking that the year is out of bounds"}
  should-throw-when-list-holidays-with-inexistent-calendar-or-WEEKEND
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendars: OMG" (holi/list-holidays 1930 "OMG")))
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendars: WATIZIT" (holi/list-holidays 2200 "WATIZIT")))
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendar: WEEKEND" (holi/list-holidays 2200 "WEEKEND"))))
