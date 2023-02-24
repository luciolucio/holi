(ns luciolucio.holi.generate-test.generate-performance-test
  (:require [clojure.test :as ct]
            [criterium.core :as criterium]
            [luciolucio.holi :as holi]
            [luciolucio.holi.file :as file])
  (:import (java.time LocalDate)))

(def root-path "test-resources/file/performance")
(def output-path "test-output")

(ct/deftest ^:performance should-generate-a-datelist-in-under-10-nanoseconds
  (println "Running benchmark for luciolucio.holi.file/generate-datelist!:")
  (let [hol-filename "test-resources/file/performance/BR.hol"
        benchmark-result (criterium/benchmark
                          (fn [] (file/generate-datelist! root-path hol-filename output-path 2020 80))
                          nil)
        mean-generation-time-in-s (-> benchmark-result :mean first)
        less-than-10-ns? (< mean-generation-time-in-s 10e-9)]
    (criterium/report-result benchmark-result)
    (println "Did it take less than 10ns?" less-than-10-ns?)

    (ct/is less-than-10-ns?)))

(ct/deftest ^:performance should-add-business-days-in-under-50-millis
  (println "Running benchmark for luciolucio.holi/add:")
  (let [benchmark-result (criterium/benchmark
                          (holi/add (LocalDate/of 2023 2 16) 5 :business-days "BR")
                          nil)
        mean-in-s (-> benchmark-result :mean first)
        less-than-50ms? (< mean-in-s 50e-3)]
    (criterium/report-result benchmark-result)
    (println "Did it take less than 50ms?" less-than-50ms?)

    (ct/is less-than-50ms?)))
