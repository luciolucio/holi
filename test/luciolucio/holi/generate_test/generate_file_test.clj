(ns luciolucio.holi.generate-test.generate-file-test
  (:require [clojure.test :refer :all]
            [luciolucio.holi.file :as file])
  (:import (clojure.lang ExceptionInfo)))

(def root-path "test-resources/file/")
(def output-path "test-resources/file/output")

(defn run-generate-datelist-test-case [expected holiday-file year bracket-size]
  (file/generate-datelist! root-path (format "test-resources/file/%s.hol" holiday-file) output-path year bracket-size)
  (is (= (slurp (format "test-resources/file/%s" expected)) (slurp (format "test-resources/file/output/%s.datelist" holiday-file)))))

(deftest should-generate-expected-files-when-generate-datelist!
  (are [expected holiday-file year bracket-size]
       (run-generate-datelist-test-case expected holiday-file year bracket-size)
    "FILE-EXPECTED.datelist" "FILE" 2020 1
    "FILE2-EXPECTED.datelist" "FILE" 2020 2
    "INCLUDE-EXPECTED.datelist" "INCLUDE" 2020 1
    "SUBDIR-FILE-EXPECTED.datelist" "subdir/FILE" 2020 1))

(deftest should-generate-weekend-file-when-generate-weekend-datelist!
  (file/generate-weekend-datelist! output-path 2020 2)
  (is (= (slurp "test-resources/file/WEEKEND-EXPECTED.datelist") (slurp "test-resources/file/output/WEEKEND.datelist"))))

(deftest should-not-allow-files-called-weekend-dot-hol-when-generate-datelist!
  (is (thrown? ExceptionInfo (file/generate-datelist! root-path "test-resources/file/WEEKEND.hol" output-path 2020 1)))
  (is (thrown? ExceptionInfo (file/generate-datelist! root-path "path/doesnt/matter/wEEkenD.hol" output-path 2020 1))))
