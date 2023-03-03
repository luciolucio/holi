(ns luciolucio.holi.add-test.business-days-multi-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-calculate-correct-date-when-add-date-with-business-days-multi-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-07-30") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    2 (t/date "2020-08-04")
    1 (t/date "2020-07-31")
    5 (t/date "2020-08-07")
    -5 (t/date "2020-07-22")))

(ct/deftest should-calculate-correct-date-when-add-date-time-with-business-days-multi-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-07-30T11:11:00") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    2 (t/date-time "2020-08-04T11:11:00")
    1 (t/date-time "2020-07-31T11:11:00")
    5 (t/date-time "2020-08-07T11:11:00")
    -5 (t/date-time "2020-07-22T11:11:00")))

(ct/deftest should-calculate-correct-date-when-add-saturday-date-with-business-days-multi-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-08-01") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    1 (t/date "2020-08-04")
    7 (t/date "2020-08-12")
    -1 (t/date "2020-07-31")
    -5 (t/date "2020-07-24")))

(ct/deftest should-calculate-correct-date-when-add-saturday-date-time-with-business-days-multi
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-08-01T22:15:09") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    1 (t/date-time "2020-08-04T22:15:09")
    7 (t/date-time "2020-08-12T22:15:09")
    -1 (t/date-time "2020-07-31T22:15:09")
    -5 (t/date-time "2020-07-24T22:15:09")))

(ct/deftest should-calculate-correct-date-when-add-sunday-date-with-business-days-multi-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date "2020-08-02") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    1 (t/date "2020-08-04")
    7 (t/date "2020-08-12")
    -1 (t/date "2020-07-31")
    -5 (t/date "2020-07-24")))

(ct/deftest should-calculate-correct-date-when-add-sunday-date-time-with-business-days-multi-calendar
  (ct/are [days expected]
          (= expected (holi/add (t/date-time "2020-08-02T03:15") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    1 (t/date-time "2020-08-04T03:15")
    7 (t/date-time "2020-08-12T03:15")
    -1 (t/date-time "2020-07-31T03:15")
    -5 (t/date-time "2020-07-24T03:15")))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-with-business-days-multi-calendar
  (ct/are [start-date expected-end-date]
          (= (t/date expected-end-date) (holi/add (t/date start-date) 0 :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    "2020-07-28" "2020-07-28"
    "2020-07-29" "2020-07-30"
    "2020-07-30" "2020-07-30"
    "2020-07-31" "2020-07-31"
    "2020-08-01" "2020-08-04"
    "2020-08-02" "2020-08-04"
    "2020-08-03" "2020-08-04"
    "2020-08-04" "2020-08-04"))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-date-time-with-business-days-multi-calendar
  (ct/are [start-date expected-end-date]
          (= (t/date-time (str expected-end-date "T03:15")) (holi/add (t/date-time (str start-date "T03:15")) 0 :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    "2020-07-28" "2020-07-28"
    "2020-07-29" "2020-07-30"
    "2020-07-30" "2020-07-30"
    "2020-07-31" "2020-07-31"
    "2020-08-01" "2020-08-04"
    "2020-08-02" "2020-08-04"
    "2020-08-03" "2020-08-04"
    "2020-08-04" "2020-08-04"))

(ct/deftest
  ^{:doc "This test relies on datelists DAY-THREE and DAY-TWENTY-NINE-2021, which list 3Aug20 and 29Jul21 as holidays, respectively.
          Any result before 2020 or after 2021 should raise an exception"}
  should-throw-when-add-date-with-business-days-multi-calendar-and-result-beyond-limit-years
  (ct/are [date n]
          (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (t/date date) n :business-day "DAY-THREE" "DAY-TWENTY-NINE-2021"))
    "2021-08-02" 152 ; Would be 31Dec21 without the holiday, but will be out of bounds with it (note: 152 is because TEST-WEEKEND has no weekends for 2021)
    "2020-01-01" -1
    "2020-01-01" -2
    "2021-12-31" 1
    "2021-12-31" 11
    "2020-01-05" -4
    "2021-12-26" 6))

(ct/deftest
  ^{:doc "This test relies on datelists DAY-THREE and DAY-TWENTY-NINE-2021, which list 3Aug20 and 29Jul21 as holidays, respectively.
          Any result before 2020 or after 2021 should raise an exception"}
  should-throw-when-add-date-time-with-business-days-multi-calendar-and-result-beyond-limit-years
  (ct/are [date n]
          (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (t/date-time (str date "T03:15")) n :business-day "DAY-THREE" "DAY-TWENTY-NINE-2021"))
    "2021-08-02" 152 ; Would be 31Dec21 without the holiday, but will be out of bounds with it (note: 152 is because TEST-WEEKEND has no weekends for 2021)
    "2020-01-01" -1
    "2020-01-01" -2
    "2021-12-31" 1
    "2021-12-31" 11
    "2020-01-05" -4
    "2021-12-26" 6))
