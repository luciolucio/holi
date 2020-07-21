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
