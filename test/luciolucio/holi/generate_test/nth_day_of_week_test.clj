(ns luciolucio.holi.generate-test.nth-day-of-week-test
  (:require [clojure.test :as ct]
            [tick.core :as t]
            [luciolucio.holi.holidays :as gen]
            [luciolucio.holi.generate-test.generate-test-constants :as constants]))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-nth-day-of-week-rule
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/nth-day-of-week.hol"))
    [{:name "Memorial Day" :date (t/date "2016-05-30")}
     {:name "Thanksgiving" :date (t/date "2016-11-24")}
     {:name "Columbus Day" :date (t/date "2016-10-10")}] 2016
    [{:name "Memorial Day" :date (t/date "2017-05-29")}
     {:name "Thanksgiving" :date (t/date "2017-11-23")}
     {:name "Columbus Day" :date (t/date "2017-10-09")}] 2017
    [{:name "Memorial Day" :date (t/date "2018-05-28")}
     {:name "Thanksgiving" :date (t/date "2018-11-22")}
     {:name "Columbus Day" :date (t/date "2018-10-08")}] 2018
    [{:name "Memorial Day" :date (t/date "2019-05-27")}
     {:name "Thanksgiving" :date (t/date "2019-11-28")}
     {:name "Columbus Day" :date (t/date "2019-10-14")}] 2019
    [{:name "Memorial Day" :date (t/date "2020-05-25")}
     {:name "Thanksgiving" :date (t/date "2020-11-26")}
     {:name "Columbus Day" :date (t/date "2020-10-12")}] 2020)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/nth-day-of-week-months-and-days-of-week.hol"))
    [{:name "Jan Holiday" :date (t/date "2022-01-03")}
     {:name "Feb Holiday" :date (t/date "2022-02-01")}
     {:name "Mar Holiday" :date (t/date "2022-03-02")}
     {:name "Apr Holiday" :date (t/date "2022-04-07")}
     {:name "May Holiday" :date (t/date "2022-05-06")}
     {:name "Jun Holiday" :date (t/date "2022-06-04")}
     {:name "Jul Holiday" :date (t/date "2022-07-03")}
     {:name "Aug Holiday" :date (t/date "2022-08-01")}
     {:name "Sep Holiday" :date (t/date "2022-09-05")}
     {:name "Oct Holiday" :date (t/date "2022-10-03")}
     {:name "Nov Holiday" :date (t/date "2022-11-07")}
     {:name "Dec Holiday" :date (t/date "2022-12-05")}] 2022)

  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/nth-day-of-week-limit-cases.hol"))
    [{:name "Inexistent Holiday 1" :date (t/date "2021-05-03")}
     {:name "Inexistent Holiday 2" :date (t/date "2021-05-10")}
     {:name "Inexistent Holiday 3" :date (t/date "2021-05-17")}
     {:name "Inexistent Holiday 4" :date (t/date "2021-05-24")}
     {:name "Inexistent Holiday 5" :date (t/date "2021-05-31")}
     {:name "Inexistent Holiday -1" :date (t/date "2021-05-31")}
     {:name "Inexistent Holiday -2" :date (t/date "2021-05-24")}
     {:name "Inexistent Holiday -3" :date (t/date "2021-05-17")}
     {:name "Inexistent Holiday -4" :date (t/date "2021-05-10")}
     {:name "Inexistent Holiday -5" :date (t/date "2021-05-03")}] 2021
    [{:name "Inexistent Holiday 1" :date (t/date "2019-05-06")}
     {:name "Inexistent Holiday 2" :date (t/date "2019-05-13")}
     {:name "Inexistent Holiday 3" :date (t/date "2019-05-20")}
     {:name "Inexistent Holiday 4" :date (t/date "2019-05-27")}
     {:name "Inexistent Holiday -1" :date (t/date "2019-05-27")}
     {:name "Inexistent Holiday -2" :date (t/date "2019-05-20")}
     {:name "Inexistent Holiday -3" :date (t/date "2019-05-13")}
     {:name "Inexistent Holiday -4" :date (t/date "2019-05-06")}] 2019))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-nth-day-of-week-rule-and-exception
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/nth-day-of-week-exception.hol"))
    [{:name "Memorial Day" :date (t/date "2016-05-30")}] 2016
    [{:name "Memorial Day" :date (t/date "2017-05-29")}] 2017
    [{:name "Memorial Day" :date (t/date "2018-05-28")}] 2018
    [] 2019
    [{:name "Memorial Day" :date (t/date "2020-05-25")}] 2020))

(ct/deftest should-generate-holidays-only-after-specified-year-when-holidays-for-year-with-nth-day-of-week-rule-and-start-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/nth-day-of-week-start.hol"))
    [] 2016
    [] 2017
    [{:name "Memorial Day" :date (t/date "2018-05-28")}] 2018
    [{:name "Memorial Day" :date (t/date "2019-05-27")}] 2019
    [{:name "Memorial Day" :date (t/date "2020-05-25")}] 2020))

(ct/deftest should-generate-holidays-only-after-specified-year-when-holidays-for-year-with-nth-day-of-week-rule-and-start-clause-and-exception
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/nth-day-of-week-start-exception.hol"))
    [] 2016
    [] 2017
    [{:name "Memorial Day" :date (t/date "2018-05-28")}] 2018
    [{:name "Memorial Day" :date (t/date "2019-05-27")}] 2019
    [] 2020))

(ct/deftest should-generate-holidays-only-after-specified-year-when-holidays-for-year-with-nth-day-of-week-rule-and-end-clause
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/nth-day-of-week-end.hol"))
    [{:name "Memorial Day" :date (t/date "2016-05-30")}] 2016
    [{:name "Memorial Day" :date (t/date "2017-05-29")}] 2017
    [{:name "Memorial Day" :date (t/date "2018-05-28")}] 2018
    [] 2019
    [] 2020))

(ct/deftest should-generate-holidays-only-after-specified-year-when-holidays-for-year-with-nth-day-of-week-rule-and-end-clause-and-exception
  (ct/are [expected year]
          (= expected (gen/holidays-for-year year constants/TEST-ROOT "test-resources/generate/nth-day-of-week-end-exception.hol"))
    [] 2016
    [{:name "Memorial Day" :date (t/date "2017-05-29")}] 2017
    [{:name "Memorial Day" :date (t/date "2018-05-28")}] 2018
    [] 2019
    [] 2020))
