(ns luciolucio.holi.generate-test.holidays-ddmmmyyyy-test
  (:require [clojure.test :as ct]
            [tick.alpha.api :as t]
            [luciolucio.holi.holidays :as gen]
            [luciolucio.holi.generate-test.generate-test-constants :as constants]))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-ddmmmyyyy
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/ddmmmyyyy.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Start of Scorpio Sign 2015" :date (t/date "2015-10-23")}] 2015
    [] 2016
    [] 2017
    [] 2018))
