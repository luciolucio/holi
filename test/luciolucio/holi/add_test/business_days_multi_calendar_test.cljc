(ns luciolucio.holi.add-test.business-days-multi-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :refer [defcalendartest]]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(defn- ->d [s] (t/date s))
(defn- ->dt [s] (t/date-time (str s "T11:11:00")))

(defn- all-cases-pass? [date days expected-sat-sun expected-fri-sat]
  (and
    ; date + sat-sun
   (= (->d expected-sat-sun) (holi/add (->d date) days :business-days :sat-sun "DAY-THREE" "DAY-TWENTY-NINE"))
    ; date + fri-sat
   (= (->d expected-fri-sat) (holi/add (->d date) days :business-days :fri-sat "DAY-THREE" "DAY-TWENTY-NINE"))
    ; date-time + sat-sun
   (= (->dt expected-sat-sun) (holi/add (->dt date) days :business-days :sat-sun "DAY-THREE" "DAY-TWENTY-NINE"))
    ; date-time + fri-sat
   (= (->dt expected-fri-sat) (holi/add (->dt date) days :business-days :fri-sat "DAY-THREE" "DAY-TWENTY-NINE"))))

(defcalendartest should-calculate-correct-date-when-add-with-business-days-multi-calendar
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-07-30" days expected-sat-sun expected-fri-sat)
    2 "2020-08-04" "2020-08-04"
    1 "2020-07-31" "2020-08-02"
    5 "2020-08-07" "2020-08-09"
    -5 "2020-07-22" "2020-07-22"))

(defcalendartest should-calculate-correct-date-when-add-friday-with-business-days-multi-calendar
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-07-31" days expected-sat-sun expected-fri-sat)
    2 "2020-08-05" "2020-08-04"
    1 "2020-08-04" "2020-08-02"
    7 "2020-08-12" "2020-08-11"
    -5 "2020-07-23" "2020-07-23"))

(defcalendartest should-calculate-correct-date-when-add-saturday-with-business-days-multi-calendar
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-08-01" days expected-sat-sun expected-fri-sat)
    1 "2020-08-04" "2020-08-02"
    7 "2020-08-12" "2020-08-11"
    -1 "2020-07-31" "2020-07-30"
    -5 "2020-07-24" "2020-07-23"))

(defcalendartest should-calculate-correct-date-when-add-sunday-with-business-days-multi-calendar
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-08-02" days expected-sat-sun expected-fri-sat)
    1 "2020-08-04" "2020-08-04"
    -1 "2020-07-31" "2020-07-30"
    -5 "2020-07-24" "2020-07-23"))

(defcalendartest should-go-to-next-business-day-or-stay-when-add-zero-days-with-business-days-multi-calendar
  (ct/are [start-date expected-sat-sun expected-fri-sat]
          (all-cases-pass? start-date 0 expected-sat-sun expected-fri-sat)
    "2020-07-28" "2020-07-28" "2020-07-28"
    "2020-07-29" "2020-07-30" "2020-07-30"
    "2020-07-30" "2020-07-30" "2020-07-30"
    "2020-07-31" "2020-07-31" "2020-08-02"
    "2020-08-01" "2020-08-04" "2020-08-02"
    "2020-08-02" "2020-08-04" "2020-08-02"
    "2020-08-03" "2020-08-04" "2020-08-04"
    "2020-08-04" "2020-08-04" "2020-08-04"))

(defcalendartest
  ^{:doc "This test relies on datelists DAY-THREE and DAY-TWENTY-NINE-2021, which list 3Aug20 and 29Jul21 as holidays, respectively.
          Any result before 2020 or after 2021 should raise an exception"}
  should-throw-when-add-date-with-business-days-multi-calendar-and-result-beyond-limit-years
  (ct/testing "date"
    (ct/testing "sat-sun"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->d date) n :business-days :sat-sun "DAY-THREE" "DAY-TWENTY-NINE-2021"))
        "2021-08-02" 152 ; Would be 31Dec21 without the holiday, but will be out of bounds with it (note: 152 is because TEST-WEEKEND has no weekends for 2021)
        "2020-01-01" -1
        "2021-12-31" 1))
    (ct/testing "fri-sat"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->d date) n :business-days :fri-sat "DAY-THREE" "DAY-TWENTY-NINE-2021"))
        "2021-08-02" 152
        "2020-01-01" -1
        "2021-12-31" 1)))

  (ct/testing "date-time"
    (ct/testing "sat-sun"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->dt date) n :business-days :sat-sun "DAY-THREE" "DAY-TWENTY-NINE-2021"))
        "2021-08-02" 152
        "2020-01-01" -1
        "2021-12-31" 1))
    (ct/testing "fri-sat"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->dt date) n :business-days :fri-sat "DAY-THREE" "DAY-TWENTY-NINE-2021"))
        "2021-08-02" 152
        "2020-01-01" -1
        "2021-12-31" 1))))
