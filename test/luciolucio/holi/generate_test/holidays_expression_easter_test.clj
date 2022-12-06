(ns luciolucio.holi.generate-test.holidays-expression-easter-test
  (:require [clojure.test :as ct]
            [tick.alpha.api :as t]
            [luciolucio.holi.holidays :as gen]
            [luciolucio.holi.generate-test.generate-test-constants :as constants]))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [{:name "Easter" :date (t/date "2013-03-31")}] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [{:name "Easter" :date (t/date "2017-04-16")}] 2017
    [{:name "Easter" :date (t/date "2018-04-01")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-exception-on-expression
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-exception.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-observance-rule
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-monday-observance-rule
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}
     {:name "Easter On Monday Again" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}
     {:name "Easter On Monday Again" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}
     {:name "Easter On Monday Again" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}
     {:name "Easter On Monday Again" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}
     {:name "Easter On Monday Again" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}
     {:name "Easter On Monday Again" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}
     {:name "Easter On Monday Again" :date (t/date "2018-04-02")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-monday-tuesday-observance-rule
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-tuesday.hol"))
    [{:name "Easter On Tuesday" :date (t/date "2012-04-10")}] 2012
    [{:name "Easter On Tuesday" :date (t/date "2013-04-02")}] 2013
    [{:name "Easter On Tuesday" :date (t/date "2014-04-22")}] 2014
    [{:name "Easter On Tuesday" :date (t/date "2015-04-07")}] 2015
    [{:name "Easter On Tuesday" :date (t/date "2016-03-29")}] 2016
    [{:name "Easter On Tuesday" :date (t/date "2017-04-18")}] 2017
    [{:name "Easter On Tuesday" :date (t/date "2018-04-03")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-start-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-start.hol"))
    [] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [{:name "Easter" :date (t/date "2017-04-16")}] 2017
    [{:name "Easter" :date (t/date "2018-04-01")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-start-clause-and-exception
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-start-exception.hol"))
    [] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [] 2017
    [{:name "Easter" :date (t/date "2018-04-01")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-with-observance-rule-and-start-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-start.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}] 2018)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-start-reverse-order.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-with-monday-observance-rule-and-start-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-start.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}
     {:name "Easter On Monday Again" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}
     {:name "Easter On Monday Again" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}
     {:name "Easter On Monday Again" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}
     {:name "Easter On Monday Again" :date (t/date "2018-04-02")}] 2018)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-start-reverse-order.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}
     {:name "Easter On Monday Again" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}
     {:name "Easter On Monday Again" :date (t/date "2016-03-28")}] 2016
    [{:name "Easter On Monday" :date (t/date "2017-04-17")}
     {:name "Easter On Monday Again" :date (t/date "2017-04-17")}] 2017
    [{:name "Easter On Monday" :date (t/date "2018-04-02")}
     {:name "Easter On Monday Again" :date (t/date "2018-04-02")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-with-monday-tuesday-observance-rule-and-start-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-tuesday-start.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Easter On Tuesday" :date (t/date "2015-04-07")}] 2015
    [{:name "Easter On Tuesday" :date (t/date "2016-03-29")}] 2016
    [{:name "Easter On Tuesday" :date (t/date "2017-04-18")}] 2017
    [{:name "Easter On Tuesday" :date (t/date "2018-04-03")}] 2018)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-tuesday-start-reverse-order.hol"))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Easter On Tuesday" :date (t/date "2015-04-07")}] 2015
    [{:name "Easter On Tuesday" :date (t/date "2016-03-29")}] 2016
    [{:name "Easter On Tuesday" :date (t/date "2017-04-18")}] 2017
    [{:name "Easter On Tuesday" :date (t/date "2018-04-03")}] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-end-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-end.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [{:name "Easter" :date (t/date "2013-03-31")}] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [] 2017
    [] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-and-end-clause-and-exception
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-end-exception.hol"))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [] 2017
    [] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-with-observance-rule-and-end-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-end.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [] 2017
    [] 2018)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-end-reverse-order.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}] 2016
    [] 2017
    [] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-with-monday-observance-rule-and-end-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-end.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}
     {:name "Easter On Monday Again" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}
     {:name "Easter On Monday Again" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}
     {:name "Easter On Monday Again" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}
     {:name "Easter On Monday Again" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}
     {:name "Easter On Monday Again" :date (t/date "2016-03-28")}] 2016
    [] 2017
    [] 2018)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-end-reverse-order.hol"))
    [{:name "Easter On Monday" :date (t/date "2012-04-09")}
     {:name "Easter On Monday Again" :date (t/date "2012-04-09")}] 2012
    [{:name "Easter On Monday" :date (t/date "2013-04-01")}
     {:name "Easter On Monday Again" :date (t/date "2013-04-01")}] 2013
    [{:name "Easter On Monday" :date (t/date "2014-04-21")}
     {:name "Easter On Monday Again" :date (t/date "2014-04-21")}] 2014
    [{:name "Easter On Monday" :date (t/date "2015-04-06")}
     {:name "Easter On Monday Again" :date (t/date "2015-04-06")}] 2015
    [{:name "Easter On Monday" :date (t/date "2016-03-28")}
     {:name "Easter On Monday Again" :date (t/date "2016-03-28")}] 2016
    [] 2017
    [] 2018))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-easter-expression-with-monday-tuesday-observance-rule-and-end-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-tuesday-end.hol"))
    [{:name "Easter On Tuesday" :date (t/date "2012-04-10")}] 2012
    [{:name "Easter On Tuesday" :date (t/date "2013-04-02")}] 2013
    [{:name "Easter On Tuesday" :date (t/date "2014-04-22")}] 2014
    [{:name "Easter On Tuesday" :date (t/date "2015-04-07")}] 2015
    [{:name "Easter On Tuesday" :date (t/date "2016-03-29")}] 2016
    [] 2017
    [] 2018)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/expression-easter-observed-monday-tuesday-end-reverse-order.hol"))
    [{:name "Easter On Tuesday" :date (t/date "2012-04-10")}] 2012
    [{:name "Easter On Tuesday" :date (t/date "2013-04-02")}] 2013
    [{:name "Easter On Tuesday" :date (t/date "2014-04-22")}] 2014
    [{:name "Easter On Tuesday" :date (t/date "2015-04-07")}] 2015
    [{:name "Easter On Tuesday" :date (t/date "2016-03-29")}] 2016
    [] 2017
    [] 2018))
