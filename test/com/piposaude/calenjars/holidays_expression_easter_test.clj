(ns com.piposaude.calenjars.holidays-expression-easter-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [com.piposaude.calenjars.holidays :as gen]))

(deftest should-generate-holidays-when-holidays-for-year-with-easter-expression
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [{:name "Easter" :date (t/date "2013-03-31")}] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [{:name "Easter" :date (t/date "2017-04-16")}] 2017
    [{:name "Easter" :date (t/date "2018-04-01")}] 2018))

(deftest should-generate-holidays-when-holidays-for-year-with-exception-on-expression
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-exception.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014))

(deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-observance-rule
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-observed.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}] 2018))

(deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-start-clause
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-start.hol"))
    [] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [{:name "Easter" :date (t/date "2017-04-16")}] 2017
    [{:name "Easter" :date (t/date "2018-04-01")}] 2018))

(deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-start-clause-and-exception
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-start-exception.hol"))
    [] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [] 2017
    [{:name "Easter" :date (t/date "2018-04-01")}] 2018))

(deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-with-observance-rule-and-start-clause
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-observed-start.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}] 2018)

  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-observed-start-reverse-order.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}] 2018))

(deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-end-clause
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-end.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [{:name "Easter" :date (t/date "2013-03-31")}] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [] 2017
    [] 2018))

(deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-end-clause-and-exception
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-end-exception.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [] 2017
    [] 2018))

(deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-with-observance-rule-and-end-clause
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-observed-end.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [] 2017
    [] 2018)

  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-easter-observed-end-reverse-order.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [] 2017
    [] 2018))
