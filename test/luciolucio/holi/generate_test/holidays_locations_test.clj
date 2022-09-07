(ns luciolucio.holi.generate-test.holidays-locations-test
  (:require [clojure.test :refer :all]
            [tick.alpha.api :as t]
            [luciolucio.holi.holidays :as gen]))

(def LOCATIONS-TEST-ROOT "test-resources/generate/locations")

(defn calendar-filename [location]
  (format "test-resources/generate/locations/%s.hol" location))

(deftest should-generate-holidays-when-holidays-for-year-with-specific-countries
  (are [country-name year expected]
       (= expected (gen/holidays-for-year year LOCATIONS-TEST-ROOT (calendar-filename country-name)))
    "BR" 2012 [{:name "Confraternização Universal / Ano Novo" :date (t/date "2012-01-01")}
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
    "BR" 2019 [{:name "Confraternização Universal / Ano Novo" :date (t/date "2019-01-01")}
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
               {:name "Natal" :date (t/date "2019-12-25")}]
    "US" 2022 [{:name "New Year's Day" :date (t/date "2022-01-01")}
               {:name "Martin Luther King Jr. Day" :date (t/date "2022-01-17")}
               {:name "President's Day" :date (t/date "2022-02-21")}
               {:name "Memorial Day" :date (t/date "2022-05-30")}
               {:name "Juneteenth" :date (t/date "2022-06-20")}
               {:name "Independence Day" :date (t/date "2022-07-04")}
               {:name "Labor Day" :date (t/date "2022-09-05")}
               {:name "Columbus Day" :date (t/date "2022-10-10")}
               {:name "Veterans Day" :date (t/date "2022-11-11")}
               {:name "Thanksgiving" :date (t/date "2022-11-24")}
               {:name "Christmas" :date (t/date "2022-12-26")}]))

(deftest should-generate-holidays-when-holidays-for-year-with-specific-cities
  (are [city-name year expected]
       (= expected (gen/holidays-for-year year LOCATIONS-TEST-ROOT (calendar-filename city-name)))
    "brazil/sao-paulo" 2012 [{:name "Confraternização Universal / Ano Novo" :date (t/date "2012-01-01")}
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
    "brazil/sao-paulo" 2020 [{:name "Confraternização Universal / Ano Novo" :date (t/date "2020-01-01")}
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
