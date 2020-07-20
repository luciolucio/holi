(ns com.piposaude.calendars.check-holiday-file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.check :as check]))

(deftest should-get-holiday-file-errors-correctly
  (are [expected filename]
    (= expected (check/get-holiday-file-errors (str "test-resources/" filename)))
    "Holiday file cannot be empty" "empty-file.hol"
    "Holiday file cannot have blank lines" "blank-file.hol"
    nil "single-line-valid-file.hol"))
