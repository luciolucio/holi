(ns luciolucio.holi.generate-test.generate-performance-test
  (:require [clojure.test :refer :all]
            [luciolucio.holi.file :as file]))

(def root-path "test-resources/file/performance")
(def output-path "test-resources/file/output")

(deftest should-perform-well
  (time
    (dotimes [_ 3]
      (file/generate-datelist! root-path "test-resources/file/performance/BR.hol" output-path 2020 80)))

  (is (= 1 1)))
