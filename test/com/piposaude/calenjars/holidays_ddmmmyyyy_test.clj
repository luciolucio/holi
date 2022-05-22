(ns com.piposaude.calenjars.holidays-ddmmmyyyy-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [com.piposaude.calenjars.holidays :as gen]))

(deftest should-generate-holidays-when-holidays-for-year-with-ddmmmyyyy
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmmyyyy.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Start of Scorpio Sign 2015" :date (t/date "2015-10-23")}] 2015
    [] 2016
    [] 2017
    [] 2018))
