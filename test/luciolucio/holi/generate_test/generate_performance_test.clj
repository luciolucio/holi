(ns luciolucio.holi.generate-test.generate-performance-test
  (:require [clojure.test :as ct]
            [luciolucio.holi.file :as file]))

(def root-path "test-resources/file/performance")
(def output-path "test-output")

(ct/deftest ^:performance should-perform-well
  (time
   (dotimes [_ 10]
     (file/generate-datelist! root-path "test-resources/file/performance/BR.hol" output-path 2020 80)))

  (ct/is (= 1 1)))
