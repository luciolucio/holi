(ns com.piposaude.relative-date-test.business-days-multi-calendar-test
  (:require [clojure.test :refer :all]
            [com.piposaude.relative-date-add :refer [relative-date-add]]
            [tick.core :as t]))

(deftest should-calculate-correct-date-when-relative-date-add-date-with-business-days-multi-calendar
  (are [days expected]
    (= expected (relative-date-add (t/date "2020-07-30") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    2 (t/date "2020-08-04")
    1 (t/date "2020-07-31")
    5 (t/date "2020-08-07")
    -5 (t/date "2020-07-22")))

(deftest should-calculate-correct-date-when-relative-date-add-date-time-with-business-days-multi-calendar
  (are [days expected]
    (= expected (relative-date-add (t/date-time "2020-07-30T11:11:00") days :business-days  "DAY-THREE" "DAY-TWENTY-NINE"))
    2 (t/date-time "2020-08-04T11:11:00")
    1 (t/date-time "2020-07-31T11:11:00")
    5 (t/date-time "2020-08-07T11:11:00")
    -5 (t/date-time "2020-07-22T11:11:00")))

(deftest should-calculate-correct-date-when-relative-date-add-saturday-date-with-business-days-multi-calendar
  (are [days expected]
    (= expected (relative-date-add (t/date "2020-08-01") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    0 (t/date "2020-08-01")
    1 (t/date "2020-08-04")
    7 (t/date "2020-08-12")
    -1 (t/date "2020-07-31")
    -5 (t/date "2020-07-24")))

(deftest should-calculate-correct-date-when-relative-date-add-saturday-date-time-with-business-days-multi
  (are [days expected]
    (= expected (relative-date-add (t/date-time "2020-08-01T22:15:09") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    0 (t/date-time "2020-08-01T22:15:09")
    1 (t/date-time "2020-08-04T22:15:09")
    7 (t/date-time "2020-08-12T22:15:09")
    -1 (t/date-time "2020-07-31T22:15:09")
    -5 (t/date-time "2020-07-24T22:15:09")))

(deftest should-calculate-correct-date-when-relative-date-add-sunday-date-with-business-days-multi-calendar
  (are [days expected]
    (= expected (relative-date-add (t/date "2020-08-02") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    0 (t/date "2020-08-02")
    1 (t/date "2020-08-04")
    7 (t/date "2020-08-12")
    -1 (t/date "2020-07-31")
    -5 (t/date "2020-07-24")))

(deftest should-calculate-correct-date-when-relative-date-add-sunday-date-time-with-business-days-multi-calendar
  (are [days expected]
    (= expected (relative-date-add (t/date-time "2020-08-02T03:15") days :business-days "DAY-THREE" "DAY-TWENTY-NINE"))
    0 (t/date-time "2020-08-02T03:15")
    1 (t/date-time "2020-08-04T03:15")
    7 (t/date-time "2020-08-12T03:15")
    -1 (t/date-time "2020-07-31T03:15")
    -5 (t/date-time "2020-07-24T03:15")))
