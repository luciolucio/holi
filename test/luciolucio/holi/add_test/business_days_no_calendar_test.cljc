(ns luciolucio.holi.add-test.business-days-no-calendar-test
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
   (= (->d expected-sat-sun) (holi/add (->d date) days :business-days :sat-sun))
    ; date + fri-sat
   (= (->d expected-fri-sat) (holi/add (->d date) days :business-days :fri-sat))
    ; date-time + sat-sun
   (= (->dt expected-sat-sun) (holi/add (->dt date) days :business-days :sat-sun))
    ; date-time + fri-sat
   (= (->dt expected-fri-sat) (holi/add (->dt date) days :business-days :fri-sat))))

(ct/deftest should-calculate-correct-date-when-add-with-business-days
  (ct/are [date days expected-sat-sun expected-fri-sat]
          (all-cases-pass? date days expected-sat-sun expected-fri-sat)
    "2020-07-30" 1 "2020-07-31" "2020-08-02"
    "2020-07-30" 2 "2020-08-03" "2020-08-03"
    "2020-07-29" -3 "2020-07-24" "2020-07-26"))

(ct/deftest should-calculate-correct-date-when-add-friday-with-business-days
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-07-31" days expected-sat-sun expected-fri-sat)
    1 "2020-08-03" "2020-08-02"
    2 "2020-08-04" "2020-08-03"
    -1 "2020-07-30" "2020-07-30"
    -5 "2020-07-24" "2020-07-26"))

(ct/deftest should-calculate-correct-date-when-add-saturday-with-business-days
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-08-01" days expected-sat-sun expected-fri-sat)
    1 "2020-08-03" "2020-08-02"
    7 "2020-08-11" "2020-08-10"
    -1 "2020-07-31" "2020-07-30"
    -5 "2020-07-27" "2020-07-26"))

(ct/deftest should-calculate-correct-date-when-add-sunday-with-business-days
  (ct/are [days expected-sat-sun expected-fri-sat]
          (all-cases-pass? "2020-08-02" days expected-sat-sun expected-fri-sat)
    1 "2020-08-03" "2020-08-03"
    5 "2020-08-07" "2020-08-09"
    -1 "2020-07-31" "2020-07-30"
    -5 "2020-07-27" "2020-07-26"))

(ct/deftest should-go-to-next-business-day-or-stay-when-add-zero-days-with-business-days
  (ct/are [start-date expected-sat-sun expected-fri-sat]
          (all-cases-pass? start-date 0 expected-sat-sun expected-fri-sat)
    "2020-07-30" "2020-07-30" "2020-07-30"
    "2020-07-31" "2020-07-31" "2020-08-02"
    "2020-08-01" "2020-08-03" "2020-08-02"
    "2020-08-02" "2020-08-03" "2020-08-02"
    "2020-08-03" "2020-08-03" "2020-08-03"))

(ct/deftest
  ^{:doc "This test relies on TEST-WEEKEND-SAT-SUN and TEST-WEEKEND-FRI-SAT, which list weekends in 2020.
          Any result outside 2020 should raise an exception"}
  should-throw-when-add-date-with-business-days-and-result-beyond-limit-years
  (ct/testing "date"
    (ct/testing "sat-sun"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->d date) n :business-days :sat-sun))
        "2020-01-01" -1
        "2020-12-31" 1))
    (ct/testing "fri-sat"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->d date) n :business-days :fri-sat))
        "2020-01-01" -1
        "2020-12-31" 1)))

  (ct/testing "date-time"
    (ct/testing "sat-sun"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->dt date) n :business-days :sat-sun))
        "2020-01-01" -1
        "2020-12-31" 1))
    (ct/testing "fri-sat"
      (ct/are [date n]
              (thrown-with-msg? ExceptionInfo #"Resulting date is out of bounds" (holi/add (->dt date) n :business-days :fri-sat))
        "2020-01-01" -1
        "2020-12-31" 1))))
