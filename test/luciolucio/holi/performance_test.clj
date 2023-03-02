(ns luciolucio.holi.performance-test
  (:require [clojure.test :as ct]
            [criterium.core :as criterium]
            [luciolucio.holi :as holi]
            [luciolucio.holi.file :as file])
  (:import (java.time LocalDate)))

(def root-path "test-resources/file/performance")
(def output-path "test-output")

; Set to true for manual runs
(def run-quick? (boolean (System/getenv "QUICK_PERF_TESTS")))

(defmacro run-benchmark [expression]
  `(if run-quick?
     (criterium/quick-benchmark ~expression nil)
     (criterium/benchmark ~expression nil)))

(ct/deftest ^:performance-long should-generate-a-datelist-in-under-10-seconds
  (println "Running benchmark for luciolucio.holi.file/generate-datelist!:")
  (let [hol-filename "test-resources/file/performance/BR.hol"
        benchmark-result (run-benchmark (file/generate-datelist! root-path hol-filename output-path 2020 80))
        mean-generation-time-in-s (-> benchmark-result :mean first)
        less-than-10-seconds? (< mean-generation-time-in-s 10)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 10 seconds?" less-than-10-seconds?)

    (ct/is less-than-10-seconds?)))

(ct/deftest ^:performance should-add-business-days-with-calendar-in-under-10-microseconds
  (println "Running benchmark for luciolucio.holi/add :business-days with a calendar:")
  (let [benchmark-result (run-benchmark (holi/add (LocalDate/of 2023 2 16) 5 :business-days "BR"))
        mean-in-s (-> benchmark-result :mean first)
        less-than-10-microseconds? (< mean-in-s 10e-6)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 10µs?" less-than-10-microseconds?)

    (ct/is less-than-10-microseconds?)))

(ct/deftest ^:performance should-add-business-days-with-no-calendar-in-under-10-microseconds
  (println "Running benchmark for luciolucio.holi/add :business-days with no calendar:")
  (let [benchmark-result (run-benchmark (holi/add (LocalDate/of 2023 2 16) 5 :business-days))
        mean-in-s (-> benchmark-result :mean first)
        less-than-10-microseconds? (< mean-in-s 10e-6)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 10µs?" less-than-10-microseconds?)

    (ct/is less-than-10-microseconds?)))

(ct/deftest ^:performance should-add-days-in-under-1-microsecond
  (println "Running benchmark for luciolucio.holi/add days without calendars:")
  (let [benchmark-result (run-benchmark (holi/add (LocalDate/of 2023 2 16) 5 :days))
        mean-in-s (-> benchmark-result :mean first)
        less-than-1-microsecond? (< mean-in-s 1e-6)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 1µs?" less-than-1-microsecond?)

    (ct/is less-than-1-microsecond?)))

(ct/deftest ^:performance should-check-holiday-in-under-1-microsecond
  (println "Running benchmark for luciolucio.holi/holiday?:")
  (let [benchmark-result (run-benchmark (holi/holiday? (LocalDate/of 2023 2 16) "BR"))
        mean-in-s (-> benchmark-result :mean first)
        less-than-1-microsecond? (< mean-in-s 1e-6)]
    (criterium/report-result benchmark-result)
    (println "Is the mean less than 1µs?" less-than-1-microsecond?)

    (ct/is less-than-1-microsecond?)))
