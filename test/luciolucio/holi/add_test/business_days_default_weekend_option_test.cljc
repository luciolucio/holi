(ns luciolucio.holi.add-test.business-days-default-weekend-option-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [tick.core :as t]))

(ct/deftest should-default-to-sat-sun-weekend-option-when-add-with-no-weekend-option
  (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-days)
            (holi/add (t/date "2020-07-30") 3 :business-days :sat-sun)))
  (ct/is (= (holi/add (t/date "2020-07-30") 3 :business-day)
            (holi/add (t/date "2020-07-30") 3 :business-day :sat-sun)))
  (ct/is (= (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-days)
            (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-days :sat-sun)))
  (ct/is (= (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-day)
            (holi/add (t/date-time "2020-07-30T10:00:00") 3 :business-day :sat-sun))))
