(ns luciolucio.holi.add-test.business-days-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(defn- ->d [s] (t/date s))
(defn- ->dt [s] (t/date-time (str s "T11:11:00")))

(defn- all-cases-pass? [date days expected-sat-sun expected-fri-sat]
  (and
    ; date + sat-sun
   (= (->d expected-sat-sun) (holi/add (->d date) days :business-days :sat-sun "DAY-THREE"))
    ; date + fri-sat
   (= (->d expected-fri-sat) (holi/add (->d date) days :business-days :fri-sat "DAY-THREE"))
    ; date-time + sat-sun
   (= (->dt expected-sat-sun) (holi/add (->dt date) days :business-days :sat-sun "DAY-THREE"))
    ; date-time + fri-sat
   (= (->dt expected-fri-sat) (holi/add (->dt date) days :business-days :fri-sat "DAY-THREE"))))

(ct/deftest should-calculate-correct-date-when-add-with-business-days-calendar
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-07-30" days expected-sat-sun expected-fri-sat)
    2 "2020-08-04" "2020-08-04"
    1 "2020-07-31" "2020-08-02"
    5 "2020-08-07" "2020-08-09"
    -5 "2020-07-23" "2020-07-23"))

(ct/deftest should-calculate-correct-date-when-add-friday-with-business-days-calendar
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-07-31" days expected-sat-sun expected-fri-sat)
    1 "2020-08-04" "2020-08-02"
    7 "2020-08-12" "2020-08-11"
    -1 "2020-07-30" "2020-07-30"
    -5 "2020-07-24" "2020-07-26"))

(ct/deftest should-calculate-correct-date-when-add-saturday-with-business-days-calendar
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-08-01" days expected-sat-sun expected-fri-sat)
    1 "2020-08-04" "2020-08-02"
    7 "2020-08-12" "2020-08-11"
    -1 "2020-07-31" "2020-07-30"
    -5 "2020-07-27" "2020-07-26"))

(ct/deftest should-calculate-correct-date-when-add-sunday-with-business-days-calendar
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-08-02" days expected-sat-sun expected-fri-sat)
    1 "2020-08-04" "2020-08-04"
    7 "2020-08-12" "2020-08-12"
    -1 "2020-07-31" "2020-07-30"
    -5 "2020-07-27" "2020-07-26"))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-with-business-days-calendar
  (ct/are [date expected-sat-sun expected-fri-sat]
          (all-cases-pass? date 0 expected-sat-sun expected-fri-sat)
    "2020-07-31" "2020-07-31" "2020-08-02"
    "2020-08-01" "2020-08-04" "2020-08-02"
    "2020-08-02" "2020-08-04" "2020-08-02"
    "2020-08-03" "2020-08-04" "2020-08-04"
    "2020-08-04" "2020-08-04" "2020-08-04"))

(ct/deftest should-calculate-correct-date-when-add-with-holiday-coinciding-with-weekend
  (ct/testing "date"
    (ct/testing "sat-sun"
      (ct/is (= (->d "2020-01-22") (holi/add (->d "2020-01-18") 3 :business-days :sat-sun "HOLIDAY-ON-SAT-SUN-WEEKEND"))))
    (ct/testing "fri-sat"
      (ct/is (= (->d "2020-01-21") (holi/add (->d "2020-01-17") 3 :business-days :fri-sat "HOLIDAY-ON-FRI-SAT-WEEKEND")))))
  (ct/testing "date-time"
    (ct/testing "sat-sun"
      (ct/is (= (->dt "2020-01-22") (holi/add (->dt "2020-01-18") 3 :business-days :sat-sun "HOLIDAY-ON-SAT-SUN-WEEKEND"))))
    (ct/testing "fri-sat"
      (ct/is (= (->dt "2020-01-21") (holi/add (->dt "2020-01-17") 3 :business-days :fri-sat "HOLIDAY-ON-FRI-SAT-WEEKEND"))))))

(ct/deftest
  ^{:doc "This test relies on DAY-THREE.datelist, in which 3Aug20 is a holiday.
          Any result outside 2020 should raise an exception"}
  should-throw-when-add-with-business-days-calendar-and-result-beyond-limit-years
  (ct/testing "date"
    (ct/testing "sat-sun"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->d date) n :business-days :sat-sun "DAY-THREE"))
        "2020-08-02" 109 ; Would be 31Dec20 without the holiday, but will be out of bounds with it
        "2020-01-01" -1
        "2020-12-31" 1))
    (ct/testing "fri-sat"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->d date) n :business-days :fri-sat "DAY-THREE"))
        "2020-08-02" 109
        "2020-01-01" -1
        "2020-12-31" 1)))

  (ct/testing "date-time"
    (ct/testing "sat-sun"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->dt date) n :business-days :sat-sun "DAY-THREE"))
        "2020-08-02" 109
        "2020-01-01" -1
        "2020-12-31" 1))
    (ct/testing "fri-sat"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->dt date) n :business-days :fri-sat "DAY-THREE"))
        "2020-08-02" 109
        "2020-01-01" -1
        "2020-12-31" 1))))
