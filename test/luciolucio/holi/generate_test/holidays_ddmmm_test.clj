(ns luciolucio.holi.generate-test.holidays-ddmmm-test
  (:require [clojure.test :as ct]
            [tick.core :as t]
            [luciolucio.holi.holidays :as gen]
            [luciolucio.holi.generate-test.generate-test-constants :as constants]))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/ddmmm.hol"))
    [{:name "New Year" :date (t/date "2012-01-01")}] 2012
    [{:name "New Year" :date (t/date "2013-01-01")}] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [{:name "New Year" :date (t/date "2015-01-01")}] 2015
    [{:name "New Year" :date (t/date "2016-01-01")}] 2016))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-ddmmm-leap
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-leap.hol"))
    [{:name "Leap Day" :date (t/date "2012-02-29")}] 2012
    [] 2013
    [] 2014
    [] 2015
    [{:name "Leap Day" :date (t/date "2016-02-29")}] 2016))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-exception-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-exception.hol"))
    [{:name "NATIONAL PEANUT BUTTER AND JELLY DAY" :date (t/date "2012-04-02")}] 2012
    [] 2013
    [{:name "NATIONAL PEANUT BUTTER AND JELLY DAY" :date (t/date "2014-04-02")}] 2014))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-observance-rule-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed.hol"))
    [{:name "Independence Day" :date (t/date "2016-07-04")}] 2016
    [{:name "Independence Day" :date (t/date "2017-07-04")}] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-monday-observance-rule-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday.hol"))
    [{:name "New Year's Day" :date (t/date "2016-01-01")}] 2016
    [{:name "New Year's Day" :date (t/date "2017-01-02")}] 2017
    [{:name "New Year's Day" :date (t/date "2018-01-01")}] 2018
    [{:name "New Year's Day" :date (t/date "2019-01-01")}] 2019
    [{:name "New Year's Day" :date (t/date "2020-01-01")}] 2020
    [{:name "New Year's Day" :date (t/date "2021-01-01")}] 2021
    [{:name "New Year's Day" :date (t/date "2022-01-03")}] 2022))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-monday-tuesday-observance-rule-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-tuesday.hol"))
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

(ct/deftest should-generate-holidays-when-holidays-for-year-with-start-clause-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-start.hol"))
    [] 2012
    [] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [{:name "New Year" :date (t/date "2015-01-01")}] 2015
    [{:name "New Year" :date (t/date "2016-01-01")}] 2016))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-start-clause-and-exception-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-start-exception.hol"))
    [] 2012
    [] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [] 2015
    [{:name "New Year" :date (t/date "2016-01-01")}] 2016))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-observance-rule-and-start-clause-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-start.hol"))
    [] 2016
    [] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-start-reverse-order.hol"))
    [] 2016
    [] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-monday-observance-rule-and-start-clause-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-start.hol"))
    [] 2016
    [] 2017
    [{:name "New Year's Day" :date (t/date "2018-01-01")}] 2018
    [{:name "New Year's Day" :date (t/date "2019-01-01")}] 2019
    [{:name "New Year's Day" :date (t/date "2020-01-01")}] 2020
    [{:name "New Year's Day" :date (t/date "2021-01-01")}] 2021
    [{:name "New Year's Day" :date (t/date "2022-01-03")}] 2022)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-start-reverse-order.hol"))
    [] 2016
    [] 2017
    [{:name "New Year's Day" :date (t/date "2018-01-01")}] 2018
    [{:name "New Year's Day" :date (t/date "2019-01-01")}] 2019
    [{:name "New Year's Day" :date (t/date "2020-01-01")}] 2020
    [{:name "New Year's Day" :date (t/date "2021-01-01")}] 2021
    [{:name "New Year's Day" :date (t/date "2022-01-03")}] 2022))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-monday-tuesday-observance-rule-and-start-clause-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-tuesday-start.hol"))
    [] 2016
    [] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}] 2019
    [{:name "Christmas" :date (t/date "2020-12-25")}] 2020
    [{:name "Christmas" :date (t/date "2021-12-27")}] 2021)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-tuesday-start-reverse-order.hol"))
    [] 2016
    [] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}] 2019
    [{:name "Christmas" :date (t/date "2020-12-25")}] 2020
    [{:name "Christmas" :date (t/date "2021-12-27")}] 2021))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-end-clause-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-end.hol"))
    [{:name "New Year" :date (t/date "2012-01-01")}] 2012
    [{:name "New Year" :date (t/date "2013-01-01")}] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [] 2015
    [] 2016))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-end-clause-and-exception-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-end-exception.hol"))
    [{:name "New Year" :date (t/date "2012-01-01")}] 2012
    [] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [] 2015
    [] 2016))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-observance-rule-and-end-clause-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-end.hol"))
    [{:name "Independence Day" :date (t/date "2016-07-04")}] 2016
    [{:name "Independence Day" :date (t/date "2017-07-04")}] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [] 2020
    [] 2021)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-end-reverse-order.hol"))
    [{:name "Independence Day" :date (t/date "2016-07-04")}] 2016
    [{:name "Independence Day" :date (t/date "2017-07-04")}] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [] 2020
    [] 2021))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-monday-observance-rule-and-end-clause-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-end.hol"))
    [{:name "New Year's Day" :date (t/date "2016-01-01")}] 2016
    [{:name "New Year's Day" :date (t/date "2017-01-02")}] 2017
    [{:name "New Year's Day" :date (t/date "2018-01-01")}] 2018
    [{:name "New Year's Day" :date (t/date "2019-01-01")}] 2019
    [] 2020
    [] 2021)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-end-reverse-order.hol"))
    [{:name "New Year's Day" :date (t/date "2016-01-01")}] 2016
    [{:name "New Year's Day" :date (t/date "2017-01-02")}] 2017
    [{:name "New Year's Day" :date (t/date "2018-01-01")}] 2018
    [{:name "New Year's Day" :date (t/date "2019-01-01")}] 2019
    [] 2020
    [] 2021))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-monday-tuesday-observance-rule-and-end-clause-on-ddmmm
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-tuesday-end.hol"))
    [{:name "Christmas" :date (t/date "2016-12-27")}] 2016
    [{:name "Christmas" :date (t/date "2017-12-25")}] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}] 2019
    [] 2020
    [] 2021)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT  "test-resources/generate/ddmmm-observed-monday-tuesday-end-reverse-order.hol"))
    [{:name "Christmas" :date (t/date "2016-12-27")}] 2016
    [{:name "Christmas" :date (t/date "2017-12-25")}] 2017
    [{:name "Christmas" :date (t/date "2018-12-25")}] 2018
    [{:name "Christmas" :date (t/date "2019-12-25")}] 2019
    [] 2020
    [] 2021))
