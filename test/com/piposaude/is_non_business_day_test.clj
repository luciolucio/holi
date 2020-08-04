(ns com.piposaude.is-non-business-day-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calenjars :refer [non-business-day?]]
            [tick.core :as t]))

(deftest should-identify-non-business-days-when-non-business-day?-with-date
  (are [date calendars expected]
       (= expected (apply non-business-day? date calendars))
    (t/date "2020-08-01") [] true
    (t/date "2020-08-03") [] false
    (t/date "2020-07-29") ["DAY-THREE"] false
    (t/date "2020-07-29") ["DAY-THREE" "DAY-TWENTY-NINE"] true
    (t/date "2020-07-30") ["DAY-THREE"] false
    (t/date "2020-08-01") ["DAY-THREE"] true
    (t/date "2020-08-03") ["DAY-THREE"] true))

(deftest should-identify-non-business-days-when-non-business-day?-with-date-time
  (are [date-time calendars expected]
       (= expected (apply non-business-day? date-time calendars))
    (t/date-time "2020-08-01T00:00:00") [] true
    (t/date-time "2020-08-03T11:10:15") [] false
    (t/date-time "2020-07-29T00:11:22") ["DAY-THREE"] false
    (t/date-time "2020-07-29T00:11:22") ["DAY-THREE" "DAY-TWENTY-NINE"] true
    (t/date-time "2020-07-30T16:40") ["DAY-THREE"] false
    (t/date-time "2020-08-01T23:59:59") ["DAY-THREE"] true
    (t/date-time "2020-08-03T00:00:01") ["DAY-THREE"] true))
