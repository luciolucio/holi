(ns luciolucio.holi.generate-test.generate-performance-test
  (:require [clojure.test :as ct]
            [criterium.core :as criterium]
            [luciolucio.holi :as holi]
            [luciolucio.holi.file :as file])
  (:import (java.time LocalDate)))

(def root-path "test-resources/file/performance")
(def output-path "test-output")

; Use for manual runs
(def run-quick? false)

(defmacro run-benchmark [expression]
  `(if run-quick?
     (criterium/quick-benchmark ~expression nil)
     (criterium/benchmark ~expression nil)))

(ct/deftest ^:performance should-generate-a-datelist-in-under-10-nanoseconds
  (println "Running benchmark for luciolucio.holi.file/generate-datelist!:")
  (let [hol-filename "test-resources/file/performance/BR.hol"
        benchmark-result (run-benchmark (file/generate-datelist! root-path hol-filename output-path 2020 80))
        mean-generation-time-in-s (-> benchmark-result :mean first)
        less-than-10-ns? (< mean-generation-time-in-s 10e-9)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 10ns?" less-than-10-ns?)

    (ct/is less-than-10-ns?)))

(ct/deftest ^:performance should-add-business-days-with-calendar-in-under-50-milliseconds
  (println "Running benchmark for luciolucio.holi/add :business-days with a calendar:")
  (let [benchmark-result (run-benchmark (holi/add (LocalDate/of 2023 2 16) 5 :business-days "BR"))
        mean-in-s (-> benchmark-result :mean first)
        less-than-50ms? (< mean-in-s 50e-3)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 50ms?" less-than-50ms?)

    (ct/is less-than-50ms?)))

(ct/deftest ^:performance should-add-business-days-with-no-calendar-in-under-50-milliseconds
  (println "Running benchmark for luciolucio.holi/add :business-days with no calendar:")
  (let [benchmark-result (run-benchmark (holi/add (LocalDate/of 2023 2 16) 5 :business-days))
        mean-in-s (-> benchmark-result :mean first)
        less-than-50ms? (< mean-in-s 50e-3)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 50ms?" less-than-50ms?)

    (ct/is less-than-50ms?)))

(ct/deftest ^:performance should-add-days-in-under-100-nanoseconds
  (println "Running benchmark for luciolucio.holi/add days without calendars:")
  (let [benchmark-result (run-benchmark (holi/add (LocalDate/of 2023 2 16) 5 :days))
        mean-in-s (-> benchmark-result :mean first)
        less-than-100-ns? (< mean-in-s 100e-9)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 100ns?" less-than-100-ns?)

    (ct/is less-than-100-ns?)))

(ct/deftest ^:performance should-check-holiday-in-under-10-milliseconds
  (println "Running benchmark for luciolucio.holi/holiday?:")
  (let [benchmark-result (run-benchmark (holi/holiday? (LocalDate/of 2023 2 16) "BR"))
        mean-in-s (-> benchmark-result :mean first)
        less-than-10-ms? (< mean-in-s 10e-3)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 10ms?" less-than-10-ms?)

    (ct/is less-than-10-ms?)))
