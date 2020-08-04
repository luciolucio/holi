(ns com.piposaude.is-weekend-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calenjars :refer [weekend?]]
            [tick.core :as t]))

(deftest should-identify-weekends-when-weekend?-with-date
  (are [date expected]
       (= expected (weekend? date))
    (t/date "2020-07-31") false
    (t/date "2020-08-01") true
    (t/date "2020-08-02") true
    (t/date "2020-08-03") false))

(deftest should-identify-weekends-when-weekend?-with-date-time
  (are [date-time expected]
       (= expected (weekend? date-time))
    (t/date "2020-07-31T00:00:00") false
    (t/date "2020-08-01T11:11:11") true
    (t/date "2020-08-02T22:22:22") true
    (t/date "2020-08-03T23:59:59") false))
