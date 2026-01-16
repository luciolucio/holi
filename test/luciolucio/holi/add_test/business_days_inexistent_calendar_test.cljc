(ns luciolucio.holi.add-test.business-days-inexistent-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :refer [defcalendartest]]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(defcalendartest should-throw-when-add-date-with-inexistent-calendar-or-starting-with-WEEKEND
  (ct/testing "date"
    (ct/testing "[date n unit calendar]"
      (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): X" (holi/add (t/date "2020-08-03") 1 :business-days "X"))))
    (ct/testing "[date n unit calendar calendar]"
      (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): X, Y" (holi/add (t/date "2020-08-03") 1 :business-days "X" "Y"))))
    (ct/testing "[date n unit weekend-option calendar]"
      (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): X" (holi/add (t/date "2020-08-03") 1 :business-days :fri-sat "X"))))
    (ct/testing "[date n unit weekend-option calendar calendar]"
      (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): X, Y" (holi/add (t/date "2020-08-03") 1 :business-days :fri-sat "X" "Y")))))

  (ct/testing "date-time"
    (ct/testing "[date n unit calendar]"
      (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): X" (holi/add (t/date-time "2020-08-03T11:11:00") 1 :business-days "X"))))
    (ct/testing "[date n unit calendar calendar]"
      (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): X, Y" (holi/add (t/date-time "2020-08-03T11:11:00") 1 :business-days "X" "Y"))))
    (ct/testing "[date n unit weekend-option calendar]"
      (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): X" (holi/add (t/date-time "2020-08-03T11:11:00") 1 :business-days :fri-sat "X"))))
    (ct/testing "[date n unit weekend-option calendar calendar]"
      (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): X, Y" (holi/add (t/date-time "2020-08-03T11:11:00") 1 :business-days :fri-sat "X" "Y")))))

  (ct/testing "weekend prefix"
    (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): WEEKEND" (holi/add (t/date "2020-08-03") 1 :business-days "WEEKEND")))
    (ct/is (thrown-with-msg? ExceptionInfo #"Unknown calendar\(s\): WEEKEND-SAT-SUN" (holi/add (t/date "2020-08-03") 1 :business-days "WEEKEND-SAT-SUN")))))
