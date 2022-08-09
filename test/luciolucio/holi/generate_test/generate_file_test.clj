(ns luciolucio.holi.generate-test.generate-file-test
  (:require [clojure.test :refer :all]
            [luciolucio.holi.file :as file]))

(def output-path "test-resources/file/output")

(defn run-generate-test-case [expected holiday-file year bracket-size]
  (file/generate-datelist! (format "test-resources/file/%s.hol" holiday-file) output-path year bracket-size)
  (is (= (slurp (format "test-resources/file/%s" expected)) (slurp (format "test-resources/file/output/%s.datelist" holiday-file)))))

(deftest should-generate-expected-files-when-generate!
  (are [expected holiday-file year bracket-size]
       (run-generate-test-case expected holiday-file year bracket-size)
    "FILE-EXPECTED.datelist" "FILE" 2020 1
    "FILE2-EXPECTED.datelist" "FILE" 2020 2
    "INCLUDE-EXPECTED.datelist" "INCLUDE" 2020 1))

(deftest should-generate-weekend-file-when-generate-weekend!
  (file/generate-weekend-datelist! output-path 2020 2)
  (is (= (slurp "test-resources/file/WEEKEND2-EXPECTED.datelist") (slurp "test-resources/file/output/WEEKEND.datelist"))))
