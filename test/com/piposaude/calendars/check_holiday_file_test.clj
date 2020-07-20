(ns com.piposaude.calendars.check-holiday-file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.check :as check]))

(deftest should-get-holiday-file-errors-correctly
  (are [expected filename]
    (= expected (check/get-holiday-file-errors (str "test-resources/" filename)))
    "Holiday file cannot be empty" "empty.hol"
    "Holiday file cannot have blank lines" "blank.hol"
    "Holiday file cannot have blank lines" "blank2.hol"
    "Holiday file cannot have blank lines" "blank3.hol"
    "Line 1: holiday definition cannot have two pipes" "two-pipes.hol"
    "Line 3: holiday definition cannot have two pipes" "two-pipes2.hol"))
