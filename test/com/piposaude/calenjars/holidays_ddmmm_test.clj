(ns com.piposaude.calenjars.holidays-ddmmm-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [com.piposaude.calenjars.holidays :as gen]))

(deftest should-generate-holidays-when-holidays-for-year-with-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm.hol"))
    [{:name "New Year" :date (t/date "2012-01-01")}] 2012
    [{:name "New Year" :date (t/date "2013-01-01")}] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [{:name "New Year" :date (t/date "2015-01-01")}] 2015
    [{:name "New Year" :date (t/date "2016-01-01")}] 2016))

(deftest should-generate-holidays-when-holidays-for-year-with-ddmmm-leap
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-leap.hol"))
    [{:name "Leap Day" :date (t/date "2012-02-29")}] 2012
    [] 2013
    [] 2014
    [] 2015
    [{:name "Leap Day" :date (t/date "2016-02-29")}] 2016))

(deftest should-generate-holidays-when-holidays-for-year-with-exception-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/exception-on-ddmmm.hol"))
    [{:name "NATIONAL PEANUT BUTTER AND JELLY DAY" :date (t/date "2012-04-02")}] 2012
    [] 2013
    [{:name "NATIONAL PEANUT BUTTER AND JELLY DAY" :date (t/date "2014-04-02")}] 2014))

(deftest should-generate-holidays-when-holidays-for-year-with-observance-rule-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed.hol"))
    [{:name "Independence Day" :date (t/date "2016-07-04")}] 2016
    [{:name "Independence Day" :date (t/date "2017-07-04")}] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021))

(deftest should-generate-holidays-when-holidays-for-year-with-start-clause-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-start.hol"))
    [] 2012
    [] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [{:name "New Year" :date (t/date "2015-01-01")}] 2015
    [{:name "New Year" :date (t/date "2016-01-01")}] 2016))

(deftest should-generate-holidays-when-holidays-for-year-with-observance-rule-and-start-clause-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-start.hol"))
    [] 2016
    [] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021)

  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-start-reverse-order.hol"))
    [] 2016
    [] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021))
