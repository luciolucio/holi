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
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-exception.hol"))
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

(deftest should-generate-holidays-when-holidays-for-year-with-monday-tuesday-observance-rule-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-monday-tuesday.hol"))
    [{:name "Christmas" :date (t/date "2016-12-27")}
     {:name "Boxing Day" :date (t/date "2016-12-26")}] 2016
    [{:name "Christmas" :date (t/date "2017-12-25")}
     {:name "Boxing Day" :date (t/date "2017-12-26")}] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}
     {:name "Boxing Day" :date (t/date "2018-12-26")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}
     {:name "Boxing Day" :date (t/date "2019-12-26")}] 2019
    [{:name "Christmas" :date (t/date "2020-12-25")}
     {:name "Boxing Day" :date (t/date "2020-12-28")}] 2020
    [{:name "Christmas" :date (t/date "2021-12-27")}
     {:name "Boxing Day" :date (t/date "2021-12-28")}] 2021
    [{:name "Christmas" :date (t/date "2022-12-27")}
     {:name "Boxing Day" :date (t/date "2022-12-26")}] 2022))

(deftest should-generate-holidays-when-holidays-for-year-with-start-clause-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-start.hol"))
    [] 2012
    [] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [{:name "New Year" :date (t/date "2015-01-01")}] 2015
    [{:name "New Year" :date (t/date "2016-01-01")}] 2016))

(deftest should-generate-holidays-when-holidays-for-year-with-start-clause-and-exception-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-start-exception.hol"))
    [] 2012
    [] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [] 2015
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

(deftest should-generate-holidays-when-holidays-for-year-with-monday-tuesday-observance-rule-and-start-clause-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-monday-tuesday-start.hol"))
    [] 2016
    [] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}] 2019
    [{:name "Christmas" :date (t/date "2020-12-25")}] 2020
    [{:name "Christmas" :date (t/date "2021-12-27")}] 2021)

  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-monday-tuesday-start-reverse-order.hol"))
    [] 2016
    [] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}] 2019
    [{:name "Christmas" :date (t/date "2020-12-25")}] 2020
    [{:name "Christmas" :date (t/date "2021-12-27")}] 2021))

(deftest should-generate-holidays-when-holidays-for-year-with-end-clause-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-end.hol"))
    [{:name "New Year" :date (t/date "2012-01-01")}] 2012
    [{:name "New Year" :date (t/date "2013-01-01")}] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [] 2015
    [] 2016))

(deftest should-generate-holidays-when-holidays-for-year-with-end-clause-and-exception-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-end-exception.hol"))
    [{:name "New Year" :date (t/date "2012-01-01")}] 2012
    [] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [] 2015
    [] 2016))

(deftest should-generate-holidays-when-holidays-for-year-with-observance-rule-and-end-clause-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-end.hol"))
    [{:name "Independence Day" :date (t/date "2016-07-04")}] 2016
    [{:name "Independence Day" :date (t/date "2017-07-04")}] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [] 2020
    [] 2021)

  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-end-reverse-order.hol"))
    [{:name "Independence Day" :date (t/date "2016-07-04")}] 2016
    [{:name "Independence Day" :date (t/date "2017-07-04")}] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [] 2020
    [] 2021))

(deftest should-generate-holidays-when-holidays-for-year-with-monday-tuesday-observance-rule-and-end-clause-on-ddmmm
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-monday-tuesday-end.hol"))
    [{:name "Christmas" :date (t/date "2016-12-27")}] 2016
    [{:name "Christmas" :date (t/date "2017-12-25")}] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}] 2019
    [] 2020
    [] 2021)

  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/ddmmm-observed-monday-tuesday-end-reverse-order.hol"))
    [{:name "Christmas" :date (t/date "2016-12-27")}] 2016
    [{:name "Christmas" :date (t/date "2017-12-25")}] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}] 2019
    [] 2020
    [] 2021))
