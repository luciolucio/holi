(ns com.piposaude.calendars.check-holiday-file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.check :as check]))

(deftest should
  (is (= true (check/check-holiday-file "single-line-file.hol"))))
