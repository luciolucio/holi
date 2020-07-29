(ns com.piposaude.calendars.check-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.check :as check]))

(deftest should-identify-invalid-holiday-files-correctly
  (are [filename]
       (= false (check/valid-holiday-file? (str "test-resources/check/" filename)))
    "empty.hol"
    "blank.hol"
    "blank2.hol"
    "blank3.hol"
    "two-pipes.hol"
    "bad-definition.hol"
    "bad-date.hol"
    "bad-date2.hol"
    "bad-date3.hol"
    "bad-date4.hol"
    "bad-date5.hol"
    "bad-expression.hol"
    "bad-expression2.hol"
    "bad-expression3.hol"
    "include-before-comment.hol"
    "include-not-on-first-line.hol"
    "include-too-many-includes.hol"
    "include-nonexistent-file.hol"
    "includes-invalid-holiday-file.hol"
    "includes-holiday-that-includes-invalid.hol"
    "bad-include.hol"
    "bad-include2.hol"))

(deftest should-identify-valid-holiday-file-correctly
  (are [filename]
       (= true (check/valid-holiday-file? (str "test-resources/check/" filename)))
    "good.hol"
    "good-all-numbers.hol"
    "include-base.hol"
    "include.hol"
    "include-with-space.hol"
    "include-nested.hol"))
