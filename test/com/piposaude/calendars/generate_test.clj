(ns com.piposaude.calendars.generate-test
  (:require [clojure.test :refer :all]
            [tick.core :as t]
            [com.piposaude.calendars.generate :as gen])
  (:import (clojure.lang ExceptionInfo)))

(deftest should-throw-when-holiday-file-is-invalid
  (is (thrown-with-msg? ExceptionInfo #"Invalid holiday file" (gen/holidays-for-year 2020 "test-resources/check/bad-date.hol"))))

(deftest should-throw-when-year-is-invalid
  (is (thrown-with-msg? ExceptionInfo #"Invalid year" (gen/holidays-for-year "a" nil))))

(deftest should-generate-holidays-correctly-ddmmm
  (are [expected year]
    (= expected (gen/holidays-for-year year (str "test-resources/generate/ddmmm.hol")))
    [{:name "New Year" :date (t/date "2012-01-01")}] 2012
    [{:name "New Year" :date (t/date "2013-01-01")}] 2013
    [{:name "New Year" :date (t/date "2014-01-01")}] 2014
    [{:name "New Year" :date (t/date "2015-01-01")}] 2015
    [{:name "New Year" :date (t/date "2016-01-01")}] 2016))

(deftest should-generate-holidays-correctly-ddmmm-leap
  (are [expected year]
    (= expected (gen/holidays-for-year year (str "test-resources/generate/ddmmm-leap.hol")))
    [{:name "Leap Day" :date (t/date "2012-02-29")}] 2012
    [] 2013
    [] 2014
    [] 2015
    [{:name "Leap Day" :date (t/date "2016-02-29")}] 2016))

(deftest should-generate-holidays-correctly-ddmmyyyy
  (are [expected year]
    (= expected (gen/holidays-for-year year (str "test-resources/generate/ddmmmyyyy.hol")))
    [] 2012
    [] 2013
    [] 2014
    [{:name "Start of Scorpio Sign 2015" :date (t/date "2015-10-23")}] 2015
    [] 2016
    [] 2017
    [] 2018))

(deftest should-generate-holidays-correctly-expression
  (are [expected year]
    (= expected (gen/holidays-for-year year (str "test-resources/generate/expression.hol")))
    [{:name "Easter" :date (t/date "2012-04-08")}] 2012
    [{:name "Easter" :date (t/date "2013-03-31")}] 2013
    [{:name "Easter" :date (t/date "2014-04-20")}] 2014
    [{:name "Easter" :date (t/date "2015-04-05")}] 2015
    [{:name "Easter" :date (t/date "2016-03-27")}] 2016
    [{:name "Easter" :date (t/date "2017-04-16")}] 2017
    [{:name "Easter" :date (t/date "2018-04-01")}] 2018))

(deftest should-generate-brazil-holidays-correctly
  (are [year expected]
    (= expected (gen/holidays-for-year year "test-resources/generate/BR.hol"))
    2012 [{:name "Confraternização Universal / Ano Novo" :date (t/date "2012-01-01")}
          {:name "Segunda-feira de carnaval" :date (t/date "2012-02-20")}
          {:name "Terça-feira de carnaval" :date (t/date "2012-02-21")}
          {:name "Sexta-feira da Paixão" :date (t/date "2012-04-06")}
          {:name "Tiradentes" :date (t/date "2012-04-21")}
          {:name "Dia do Trabalho" :date (t/date "2012-05-01")}
          {:name "Corpus Christi" :date (t/date "2012-06-07")}
          {:name "Independência do Brasil" :date (t/date "2012-09-07")}
          {:name "Nossa Senhora de Aparecida" :date (t/date "2012-10-12")}
          {:name "Finados" :date (t/date "2012-11-02")}
          {:name "Proclamação da República" :date (t/date "2012-11-15")}
          {:name "Natal" :date (t/date "2012-12-25")}]
    2019 [{:name "Confraternização Universal / Ano Novo" :date (t/date "2019-01-01")}
          {:name "Segunda-feira de carnaval" :date (t/date "2019-03-04")}
          {:name "Terça-feira de carnaval" :date (t/date "2019-03-05")}
          {:name "Sexta-feira da Paixão" :date (t/date "2019-04-19")}
          {:name "Tiradentes" :date (t/date "2019-04-21")}
          {:name "Dia do Trabalho" :date (t/date "2019-05-01")}
          {:name "Corpus Christi" :date (t/date "2019-06-20")}
          {:name "Independência do Brasil" :date (t/date "2019-09-07")}
          {:name "Nossa Senhora de Aparecida" :date (t/date "2019-10-12")}
          {:name "Finados" :date (t/date "2019-11-02")}
          {:name "Proclamação da República" :date (t/date "2019-11-15")}
          {:name "Natal" :date (t/date "2019-12-25")}]))

(deftest should-generate-sao-paulo-holidays-correctly
  (are [year expected]
    (= expected (gen/holidays-for-year year "test-resources/generate/SPO.hol"))
    2012 [{:name "Confraternização Universal / Ano Novo" :date (t/date "2012-01-01")}
          {:name "Segunda-feira de carnaval" :date (t/date "2012-02-20")}
          {:name "Terça-feira de carnaval" :date (t/date "2012-02-21")}
          {:name "Sexta-feira da Paixão" :date (t/date "2012-04-06")}
          {:name "Tiradentes" :date (t/date "2012-04-21")}
          {:name "Dia do Trabalho" :date (t/date "2012-05-01")}
          {:name "Corpus Christi" :date (t/date "2012-06-07")}
          {:name "Independência do Brasil" :date (t/date "2012-09-07")}
          {:name "Nossa Senhora de Aparecida" :date (t/date "2012-10-12")}
          {:name "Finados" :date (t/date "2012-11-02")}
          {:name "Proclamação da República" :date (t/date "2012-11-15")}
          {:name "Natal" :date (t/date "2012-12-25")}
          {:name "Aniversário de São Paulo" :date (t/date "2012-01-25")}
          {:name "Revolução Constitucionalista" :date (t/date "2012-07-09")}
          {:name "Dia da Consciência Negra" :date (t/date "2012-11-20")}]))

(deftest should-generate-nested-included-holidays-correctly
  (= [{:name "Holiday from include-first" :date (t/date "2012-01-25")}
      {:name "Holiday from include-second" :date (t/date "2012-01-30")}
      {:name "Holiday from include-third" :date (t/date "2012-02-01")}]
     (gen/holidays-for-year 2012 "test-resources/generate/include-first.hol")))
