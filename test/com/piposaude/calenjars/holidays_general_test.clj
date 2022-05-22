(ns com.piposaude.calenjars.holidays-general-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [com.piposaude.calenjars.holidays :as gen])
  (:import (clojure.lang ExceptionInfo)))

(deftest should-throw-when-holiday-file-is-invalid
  (is (thrown-with-msg? ExceptionInfo #"Invalid holiday file" (gen/holidays-for-year 2020 "test-resources/check/bad-date.hol"))))

(deftest should-throw-when-year-is-invalid
  (is (thrown-with-msg? ExceptionInfo #"Invalid year" (gen/holidays-for-year "a" nil))))

(deftest should-generate-holidays-when-holidays-for-year-with-nested-includes
  (= [{:name "Holiday from include-first" :date (t/date "2012-01-25")}
      {:name "Holiday from include-second" :date (t/date "2012-01-30")}
      {:name "Holiday from include-third" :date (t/date "2012-02-01")}]
     (gen/holidays-for-year 2012 "test-resources/generate/include-first.hol")))

(deftest should-generate-holidays-when-holidays-for-year-with-brazil-holidays
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

(deftest should-generate-holidays-when-holidays-for-year-with-sao-paulo-holidays
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
          {:name "Dia da Consciência Negra" :date (t/date "2012-11-20")}]
    2020 [{:name "Confraternização Universal / Ano Novo" :date (t/date "2020-01-01")}
          {:name "Segunda-feira de carnaval" :date (t/date "2020-02-24")}
          {:name "Terça-feira de carnaval" :date (t/date "2020-02-25")}
          {:name "Sexta-feira da Paixão" :date (t/date "2020-04-10")}
          {:name "Tiradentes" :date (t/date "2020-04-21")}
          {:name "Dia do Trabalho" :date (t/date "2020-05-01")}
          {:name "Independência do Brasil" :date (t/date "2020-09-07")}
          {:name "Nossa Senhora de Aparecida" :date (t/date "2020-10-12")}
          {:name "Finados" :date (t/date "2020-11-02")}
          {:name "Proclamação da República" :date (t/date "2020-11-15")}
          {:name "Natal" :date (t/date "2020-12-25")}
          {:name "Aniversário de São Paulo" :date (t/date "2020-01-25")}
          {:name "Antecipação Corpus Christi" :date (t/date "2020-05-20")}
          {:name "Antecipação Consciência Negra" :date (t/date "2020-05-21")}
          {:name "Emenda Quarentena" :date (t/date "2020-05-22")}
          {:name "Antecipação Revolução Constitucionalista" :date (t/date "2020-05-25")}]))
