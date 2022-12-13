(ns holi-test
  (:require [luciolucio.holi :as holi]
            [tick.core :as t]
            [cljs.test :as ct]))

(ct/deftest check-calendars
  (ct/is (true? (holi/weekend? (t/date "2022-12-11"))))

  (ct/are [calendar date]
    (true? (holi/holiday? (t/date date) calendar))
    "BR" "2022-10-12"
    "US" "2022-07-04"
    "GB" "2022-06-03"
    "brazil/sao-paulo" "2022-01-25"))

(defn -main []
  (ct/run-tests 'holi-test))
