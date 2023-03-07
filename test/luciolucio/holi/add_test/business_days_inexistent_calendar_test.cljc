(ns luciolucio.holi.add-test.business-days-inexistent-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-throw-when-add-date-with-inexistent-calendar
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendars: X, Y" (holi/add (t/date "2020-08-03") 1 :business-days "X" "Y")))
  (ct/is (thrown-with-msg? ExceptionInfo #"No such calendars: X, Y" (holi/add (t/date-time "2020-08-03T00:00:00") 1 :business-days "X" "Y"))))
