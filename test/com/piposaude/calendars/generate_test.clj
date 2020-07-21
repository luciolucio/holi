(ns com.piposaude.calendars.generate-test
  (:require [clojure.test :refer :all]
            [tick.core :as t]
            [com.piposaude.calendars.generate :as gen])
  (:import (clojure.lang ExceptionInfo)))

(deftest should-throw-when-holiday-file-is-invalid
  (is (thrown-with-msg? ExceptionInfo #"Invalid holiday file" (gen/holidays-for-year 2020 "test-resources/check/bad-date.hol"))))

(deftest should-throw-when-year-is-invalid
  (is (thrown-with-msg? ExceptionInfo #"Invalid year" (gen/holidays-for-year "a" nil))))

(deftest should-generate-holidays-correctly-ddmmm
  (= [{:name "New Year" :date (t/date "2020-01-01")}] (gen/holidays-for-year 2020 "test-resources/generate/ddmmm.hol")))

(deftest should-generate-holidays-correctly-ddmmm-leap
  (are [expected year]
    (= expected (gen/holidays-for-year year (str "test-resources/generate/ddmmm-leap.hol")))
    [{:name "Leap Day" :date (t/date "2012-02-29")}] 2012
    [] 2013
    [] 2014
    [] 2015
    [{:name "Leap Day" :date (t/date "2016-02-29")}] 2016))

(deftest should-generate-holidays-correctly-ddmmyyyy
  (are [expected year]
    (= expected (gen/holidays-for-year year (str "test-resources/generate/ddmmmyyyy.hol")))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Start of Scorpio Sign 2015" :date (t/date "2015-10-23")}] 2015
    [] 2016
    [] 2017
    [] 2018))

(deftest should-generate-holidays-correctly-expression
  (are [expected year]
    (= expected (gen/holidays-for-year year (str "test-resources/generate/expression.hol")))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [{:name "Easter" :date (t/date "2013-03-31")}] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [{:name "Easter" :date (t/date "2017-04-16")}] 2017
    [{:name "Easter" :date (t/date "2018-04-01")}] 2018))
