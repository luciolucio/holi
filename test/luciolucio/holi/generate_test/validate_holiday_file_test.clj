(ns luciolucio.holi.generate-test.validate-holiday-file-test
  (:require [clojure.test :as ct]
            [luciolucio.holi.check :as check]))

(def TEST-ROOT "test-resources/check")

(ct/deftest should-identify-invalid-holiday-files-correctly
  (ct/are [filename]
          (= false (check/valid-holiday-file? TEST-ROOT (str "test-resources/check/" filename)))
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

(ct/deftest should-identify-valid-holiday-file-correctly
  (ct/are [filename]
          (= true (check/valid-holiday-file? TEST-ROOT (str "test-resources/check/" filename)))
    "good.hol"
    "good-all-numbers.hol"
    "include-base.hol"
    "include.hol"
    "include-subdir.hol"
    "include-with-space.hol"
    "include-nested.hol"))
