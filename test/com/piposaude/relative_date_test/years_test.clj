(ns com.piposaude.relative-date-test.years-test
  (:require [clojure.test :refer :all]
            [com.piposaude.relative-date-add :refer [relative-date-add]]
            [tick.core :as t]))

(deftest should-calculate-correct-date-when-relative-date-add-date-with-years
  (are [days expected]
       (= expected (relative-date-add (t/date "2020-02-29") days :years))
    0 (t/date "2020-02-29")
    1 (t/date "2021-02-28")
    11 (t/date "2031-02-28")
    365 (t/date "2385-02-28")
    -365 (t/date "1655-02-28")
    -4 (t/date "2016-02-29")
    -5 (t/date "2015-02-28")))

(deftest should-calculate-correct-date-when-relative-date-add-date-time-with-years
  (are [days expected]
       (= expected (relative-date-add (t/date-time "2020-02-29T10:15:00") days :years))
    0 (t/date-time "2020-02-29T10:15:00")
    1 (t/date-time "2021-02-28T10:15:00")
    11 (t/date-time "2031-02-28T10:15:00")
    365 (t/date-time "2385-02-28T10:15:00")
    -365 (t/date-time "1655-02-28T10:15:00")
    -4 (t/date-time "2016-02-29T10:15:00")
    -5 (t/date-time "2015-02-28T10:15:00")))
