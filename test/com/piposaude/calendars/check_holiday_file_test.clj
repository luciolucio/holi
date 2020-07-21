(ns com.piposaude.calendars.check-holiday-file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.check :as check]))

(deftest should-identify-invalid-holiday-files-correctly
  (are [filename]
    (= false (check/valid-holiday-file? (str "test-resources/" filename)))
    "empty.hol"
    "blank.hol"
    "blank2.hol"
    "blank3.hol"
    "two-pipes.hol"
    "bad-definition.hol"
    "bad-definition2.hol"
    "bad-definition3.hol"
    "bad-definition4.hol"
    "bad-definition5.hol"
    "bad-definition6.hol"
    "bad-definition7.hol"
    "bad-definition8.hol"))

(deftest should-identify-valid-holiday-file-correctly
  (is (= true (check/valid-holiday-file? "test-resources/good.hol"))))
