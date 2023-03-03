(ns luciolucio.holi.add-test.business-days-inexistent-calendar-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-throw-when-add-date-with-inexistent-calendar
  (ct/are [days date]
          (thrown-with-msg? ExceptionInfo #"No such calendars: X, Y" (holi/add date days :business-days "X" "Y"))
    1 (t/date "2020-08-03")
    1 (t/date-time "2020-08-03T11:11:00")
    0 (t/date "2020-07-31")
    0 (t/date-time "2020-07-31T03:15")
    -1 (t/date "2020-01-01")
    -1 (t/date-time "2020-01-01T00:00:00")
    5 (t/date "2020-12-26")
    5 (t/date-time "2020-12-26T23:00:00")))
