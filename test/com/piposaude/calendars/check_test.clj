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
    "bad-expression3.hol"))

; Parser results example from parsing good.hol
; ([:holiday "Holiday ddmmm" [:ddmmm "29" "Aug"]]
;  [:holiday "Holiday ddmmmyyyy" [:ddmmmyyyy [:ddmmm "29" "Aug"] [:yyyy "2019"]]]
;  [:holiday "Holiday expression plus" [:expression "E" "+" "40"]])
(deftest should-identify-valid-holiday-file-correctly
  (is (= true (check/valid-holiday-file? "test-resources/check/good.hol"))))
