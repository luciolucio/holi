(ns luciolucio.holi.generate-test.generate-file-test
  (:require [clojure.test :as ct]
            [luciolucio.holi.file :as file])
  (:import (clojure.lang ExceptionInfo)))

(def root-path "test-resources/file/")
(def output-path "test-output")

(defn run-generate-datelist-test-case [expected holiday-file central-year bracket-size]
  (file/generate-datelist! root-path (format "test-resources/file/%s.hol" holiday-file) output-path central-year bracket-size)
  (ct/is (= (slurp (format "test-resources/file/%s" expected)) (slurp (format "test-output/%s.datelist" holiday-file)))))

(ct/deftest should-generate-expected-files-when-generate-datelist!
  (ct/are [expected holiday-file year bracket-size]
          (run-generate-datelist-test-case expected holiday-file year bracket-size)
    "FILE-EXPECTED.datelist" "FILE" 2020 1
    "FILE2-EXPECTED.datelist" "FILE" 2020 2
    "MANY-HOLIDAYS-EXPECTED.datelist" "MANY-HOLIDAYS" 2020 1
    "INCLUDE-EXPECTED.datelist" "INCLUDE" 2020 1
    "NESTED-EXPECTED.datelist" "NESTED" 2020 1
    "SUBDIR-FILE-EXPECTED.datelist" "subdir/FILE" 2020 1
    "INCLUDE-SUBDIR-EXPECTED.datelist" "INCLUDE-SUBDIR" 2020 1
    "INCLUDE-ROOT-FROM-SUBDIR-EXPECTED.datelist" "subdir/INCLUDE" 2020 1
    "INCLUDE-SUBDIR-FROM-SUBDIR-EXPECTED.datelist" "subdir/INCLUDE-SUBDIR" 2020 1))

(ct/deftest should-generate-weekend-file-when-generate-weekend-datelist!
  (file/generate-weekend-datelist! output-path 2020 2)
  (ct/is (= (slurp "test-resources/file/WEEKEND-EXPECTED.datelist") (slurp "test-output/WEEKEND.datelist"))))

(ct/deftest should-not-allow-files-called-weekend-dot-hol-when-generate-datelist!
  (ct/is (thrown? ExceptionInfo (file/generate-datelist! root-path "test-resources/file/WEEKEND.hol" output-path 2020 1)))
  (ct/is (thrown? ExceptionInfo (file/generate-datelist! root-path "path/doesnt/matter/wEEkenD.hol" output-path 2020 1))))
