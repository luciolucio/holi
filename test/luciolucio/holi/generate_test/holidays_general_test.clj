(ns luciolucio.holi.generate-test.holidays-general-test
  (:require [clojure.test :as ct]
            [tick.alpha.api :as t]
            [luciolucio.holi.holidays :as gen]
            [luciolucio.holi.generate-test.generate-test-constants :as constants])
  (:import (clojure.lang ExceptionInfo)))

(ct/deftest should-throw-when-holiday-file-is-invalid
  (ct/is (thrown-with-msg? ExceptionInfo #"Invalid holiday file" (gen/holidays-for-year 2020 constants/TEST-ROOT "test-resources/check/bad-date.hol"))))

(ct/deftest should-throw-when-year-is-invalid
  (ct/is (thrown-with-msg? ExceptionInfo #"Invalid year" (gen/holidays-for-year "a" constants/TEST-ROOT nil))))

(ct/deftest should-generate-holidays-when-holidays-for-year-with-nested-includes
  (ct/is (= [{:name "Holiday from include-third" :date (t/date "2012-02-01")}
             {:name "Holiday from include-second" :date (t/date "2012-01-30")}
             {:name "Holiday from include-first" :date (t/date "2012-01-25")}]
            (gen/holidays-for-year 2012 constants/TEST-ROOT "test-resources/generate/include-first.hol"))))
