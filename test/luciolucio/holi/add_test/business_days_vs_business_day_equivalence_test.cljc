(ns luciolucio.holi.add-test.business-days-vs-business-day-equivalence-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t]))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-calculate-the-same-result-when-add-with-business-days-or-business-day
  (ct/testing "date"
    (ct/testing "[date n unit]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days)
                (holi/add (t/date "2020-07-30") 3 :business-day))))
    (ct/testing "[date n unit calendar]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days "DAY-THREE")
                (holi/add (t/date "2020-07-30") 3 :business-day "DAY-THREE"))))
    (ct/testing "[date n unit calendar calendar]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/add (t/date "2020-07-30") 3 :business-day "DAY-THREE" "DAY-TWENTY-NINE"))))
    (ct/testing "[date n unit weekend-option]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days :fri-sat)
                (holi/add (t/date "2020-07-30") 3 :business-day :fri-sat))))
    (ct/testing "[date n unit weekend-option calendar]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days :fri-sat "DAY-THREE")
                (holi/add (t/date "2020-07-30") 3 :business-day :fri-sat "DAY-THREE"))))
    (ct/testing "[date n unit weekend-option calendar calendar]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days :fri-sat "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/add (t/date "2020-07-30") 3 :business-day :fri-sat "DAY-THREE" "DAY-TWENTY-NINE")))))

  (ct/testing "date-time"
    (ct/testing "[date n unit]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-days)
                (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-day))))
    (ct/testing "[date n unit calendar]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-days "DAY-THREE")
                (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-day "DAY-THREE"))))
    (ct/testing "[date n unit calendar calendar]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-days "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-day "DAY-THREE" "DAY-TWENTY-NINE"))))
    (ct/testing "[date n unit weekend-option]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-days :fri-sat)
                (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-day :fri-sat))))
    (ct/testing "[date n unit weekend-option calendar]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-days :fri-sat "DAY-THREE")
                (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-day :fri-sat "DAY-THREE"))))
    (ct/testing "[date n unit weekend-option calendar calendar]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-days :fri-sat "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/add (t/date-time "2020-07-30T11:11:00") 3 :business-day :fri-sat "DAY-THREE" "DAY-TWENTY-NINE"))))))
