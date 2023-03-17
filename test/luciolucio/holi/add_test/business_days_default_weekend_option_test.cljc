(ns luciolucio.holi.add-test.business-days-default-weekend-option-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t]))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-default-weekend-option-to-sat-sun-when-add-with-no-weekend-option
  (ct/testing "date"
    (ct/testing "[date n unit]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days)
                (holi/add (t/date "2020-07-30") 3 :business-days :sat-sun))))
    (ct/testing "[date n unit calendar]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days "DAY-THREE")
                (holi/add (t/date "2020-07-30") 3 :business-days :sat-sun "DAY-THREE"))))
    (ct/testing "[date n unit calendar calendar]"
      (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/add (t/date "2020-07-30") 3 :business-days :sat-sun "DAY-THREE" "DAY-TWENTY-NINE")))))

  (ct/testing "datetime"
    (ct/testing "[date n unit]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-days)
                (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-days :sat-sun))))
    (ct/testing "[date n unit calendar]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-days "DAY-THREE")
                (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-days :sat-sun "DAY-THREE"))))
    (ct/testing "[date n unit calendar calendar]"
      (ct/is (= (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-days "DAY-THREE" "DAY-TWENTY-NINE")
                (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-days :sat-sun "DAY-THREE" "DAY-TWENTY-NINE"))))))
