(ns com.piposaude.calenjars.holidays-expression-test
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
       (= expected (gen/holidays-for-year year "test-resources/generate/expression-exception.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014))

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
