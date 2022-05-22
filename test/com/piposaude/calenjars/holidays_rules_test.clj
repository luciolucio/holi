(ns com.piposaude.calenjars.holidays-rules-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [com.piposaude.calenjars.holidays :as gen])
  (:import (clojure.lang ExceptionInfo)))

(deftest should-generate-holidays-when-holidays-for-year-with-nth-day-of-week-rule
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/rule-nth-day-of-week.hol"))
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

  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/rule-nth-day-of-week-limit-cases.hol"))
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

(deftest should-generate-holidays-only-after-specified-yer-when-holidays-for-year-with-nth-day-of-week-rule-and-start-clause
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/rule-nth-day-of-week-start.hol"))
    [] 2016
    [] 2017
    [{:name "Memorial Day" :date (t/date "2018-05-28")}] 2018
    [{:name "Memorial Day" :date (t/date "2019-05-27")}] 2019
    [{:name "Memorial Day" :date (t/date "2020-05-25")}] 2020))

(deftest should-generate-holidays-when-holidays-for-year-with-observance-rule
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/rule-observed.hol"))
    [{:name "Independence Day" :date (t/date "2016-07-04")}] 2016
    [{:name "Independence Day" :date (t/date "2017-07-04")}] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021))

(deftest should-generate-holidays-only-after-specified-year-when-holidays-for-year-with-observance-rule-and-start-clause
  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/rule-observed-start.hol"))
    [] 2016
    [] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021)

  (are [expected year]
       (= expected (gen/holidays-for-year year "test-resources/generate/rule-observed-start-reverse-order.hol"))
    [] 2016
    [] 2017
    [{:name "Independence Day" :date (t/date "2018-07-04")}] 2018
    [{:name "Independence Day" :date (t/date "2019-07-04")}] 2019
    [{:name "Independence Day" :date (t/date "2020-07-03")}] 2020
    [{:name "Independence Day" :date (t/date "2021-07-05")}] 2021))
