(ns com.piposaude.calendars.generate-test
  (:require [clojure.test :refer :all]
            [tick.core :as t]
            [com.piposaude.calendars.generate :as gen]))

(deftest should-generate-holidays-correctly-ddmmm
  (= [(t/date "2020-01-01")] (gen/holidays-for-year 2020 "test-resources/generate/first-january.hol")))
