(ns holi-test
  (:require [luciolucio.holi :as holi]
            [tick.core :as t]
            [clojure.test :as ct]))

(ct/deftest check-calendars
  (ct/testing "holiday?"
    (ct/testing "[date calendar]"
      (ct/are [calendar date]
        (true? (holi/holiday? (t/date date) calendar))
        "BR" "2022-10-12"
        "US" "2022-07-04"
        "GB" "2022-06-03"
        "brazil/sao-paulo" "2022-01-25"))))

(ct/deftest check-signatures-exist
  (ct/testing "add"
    (ct/testing "[date n unit]"
      (ct/is (= (t/date "2020-10-11") (holi/add (t/date "2020-10-10") 1 :day))))
    (ct/testing "[date n unit calendar]"
      (ct/is (= (t/date "2020-10-13") (holi/add (t/date "2020-10-11") 1 :business-day "BR"))))
    (ct/testing "[date n unit calendar calendar]"
      (ct/is (= (t/date "2020-05-26") (holi/add (t/date "2020-05-22") 1 :business-day "BR" "US"))))
    (ct/testing "[date n unit weekend-option]"
      (ct/is (= (t/date "2020-10-11") (holi/add (t/date "2020-10-10") 1 :day :sat-sun))))
    (ct/testing "[date n unit weekend-option calendar]"
      (ct/is (= (t/date "2020-10-13") (holi/add (t/date "2020-10-11") 1 :business-day :sat-sun "BR"))))
    (ct/testing "[date n unit weekend-option calendar calendar]"
      (ct/is (= (t/date "2020-05-26") (holi/add (t/date "2020-05-22") 1 :business-day :sat-sun "BR" "US")))))

  (ct/testing "weekend?"
    (ct/testing "[date]"
      (ct/is (true? (holi/weekend? (t/date "2022-12-11")))))
    (ct/testing "[date weekend-option]"
      (ct/is (true? (holi/weekend? (t/date "2022-12-09") :fri-sat)))))

  (ct/testing "non-business-day?"
    (ct/testing "[date]"
      (ct/is (true? (holi/non-business-day? (t/date "2020-12-13")))))
    (ct/testing "[date calendar]"
      (ct/is (true? (holi/non-business-day? (t/date "2020-10-12") "BR"))))
    (ct/testing "[date calendar calendar]"
      (ct/is (true? (holi/non-business-day? (t/date "2020-05-25") "BR" "US"))))
    (ct/testing "[date weekend-option]"
      (ct/is (true? (holi/non-business-day? (t/date "2020-12-11") :fri-sat))))
    (ct/testing "[date weekend-option calendar]"
      (ct/is (true? (holi/non-business-day? (t/date "2020-10-12") :sat-sun "BR"))))
    (ct/testing "[date weekend-option calendar calendar]"
      (ct/is (true? (holi/non-business-day? (t/date "2020-05-25") :sat-sun "BR" "US")))))

  (ct/testing "business-day?"
    (ct/testing "[date]"
      (ct/is (false? (holi/business-day? (t/date "2020-12-13")))))
    (ct/testing "[date calendar]"
      (ct/is (false? (holi/business-day? (t/date "2020-10-12") "BR"))))
    (ct/testing "[date calendar calendar]"
      (ct/is (false? (holi/business-day? (t/date "2020-05-25") "BR" "US"))))
    (ct/testing "[date weekend-option]"
      (ct/is (false? (holi/business-day? (t/date "2020-12-11") :fri-sat))))
    (ct/testing "[date weekend-option calendar]"
      (ct/is (false? (holi/business-day? (t/date "2020-10-12") :sat-sun "BR"))))
    (ct/testing "[date weekend-option calendar calendar]"
      (ct/is (false? (holi/business-day? (t/date "2020-05-25") :sat-sun "BR" "US")))))

  (ct/testing "holidays-in-year"
    (ct/testing "[year calendar]"
      (ct/is (coll? (holi/holidays-in-year 2020 "BR")))))

  (ct/testing "holidays-in-date"
    (ct/testing "[date calendar]"
      (ct/is (coll? (holi/holidays-in-date (t/date "2020-10-12") "BR"))))))

(defn -main []
  (ct/run-tests 'holi-test))
