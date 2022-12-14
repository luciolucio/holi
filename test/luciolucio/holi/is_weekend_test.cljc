(ns luciolucio.holi.is-weekend-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [luciolucio.holi.test-setup :as setup]
            [tick.core :as t]))

(ct/use-fixtures :each setup/test-datelist-fixture)

(ct/deftest should-identify-weekends-when-weekend?-with-date
  (ct/are [date expected]
          (= expected (holi/weekend? date))
    (t/date "2020-07-31") false
    (t/date "2020-08-01") true
    (t/date "2020-08-02") true
    (t/date "2020-08-03") false))

(ct/deftest should-identify-weekends-when-weekend?-with-date-time
  (ct/are [date-time expected]
          (= expected (holi/weekend? date-time))
    (t/date-time "2020-07-31T00:00:00") false
    (t/date-time "2020-08-01T11:11:11") true
    (t/date-time "2020-08-02T22:22:22") true
    (t/date-time "2020-08-03T23:59:59") false))
