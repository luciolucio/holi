(ns com.piposaude.calendars.file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.file :as file]))

(def output-path "test-resources/file/output")

(defn run-generate-test-case [expected holiday-file year bracket-size]
  (file/generate! (format "test-resources/file/%s.hol" holiday-file) output-path year bracket-size)
  (is (= (slurp (format "test-resources/file/%s" expected)) (slurp (format "test-resources/file/output/%s" holiday-file)))))

(deftest blah
  (are [expected holiday-file year bracket-size]
    (run-generate-test-case expected holiday-file year bracket-size)
    "FILE-EXPECTED" "FILE" 2020 1
    "FILE2-EXPECTED" "FILE" 2020 2
    "INCLUDE-EXPECTED" "INCLUDE" 2020 1))

(deftest should-generate-weekend-file-when-generate
  (file/generate! "test-resources/file/FILE.hol" output-path 2020 2)
  (is (= (slurp "test-resources/file/FILE2-EXPECTED") (slurp "test-resources/file/output/FILE")))
  (is (= (slurp "test-resources/file/WEEKEND2-EXPECTED") (slurp "test-resources/file/output/WEEKEND"))))
