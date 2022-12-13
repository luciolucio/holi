(ns holi-test
  (:require [luciolucio.holi :as holi]
            [tick.core :as t]
            [clojure.test :as ct]))

(ct/deftest check-calendars
  (ct/is (true? (holi/holiday? (t/date "2022-10-12") "BR"))))

(defn -main []
  (ct/run-tests 'holi-test))
