(ns luciolucio.holi.is-business-day-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t]))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-identify-business-days-when-business-days?-with-date
  (ct/are [date calendars expected]
          (= expected (apply holi/business-day? date calendars))
    (t/date "2020-08-01") [] false
    (t/date "2020-08-03") [] true
    (t/date "2020-07-29") ["DAY-THREE"] true
    (t/date "2020-07-29") ["DAY-THREE" "DAY-TWENTY-NINE"] false
    (t/date "2020-07-30") ["DAY-THREE"] true
    (t/date "2020-08-01") ["DAY-THREE"] false
    (t/date "2020-08-03") ["DAY-THREE"] false))

(ct/deftest should-identify-business-days-when-business-days?-with-date-time
  (ct/are [date-time calendars expected]
          (= expected (apply holi/business-day? date-time calendars))
    (t/date-time "2020-08-01T00:00:00") [] false
    (t/date-time "2020-08-03T11:10:15") [] true
    (t/date-time "2020-07-29T00:11:22") ["DAY-THREE"] true
    (t/date-time "2020-07-29T00:11:22") ["DAY-THREE" "DAY-TWENTY-NINE"] false
    (t/date-time "2020-07-30T16:40") ["DAY-THREE"] true
    (t/date-time "2020-08-01T23:59:59") ["DAY-THREE"] false
    (t/date-time "2020-08-03T00:00:01") ["DAY-THREE"] false))
