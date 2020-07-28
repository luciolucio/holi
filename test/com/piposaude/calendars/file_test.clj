(ns com.piposaude.calendars.file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.file :as file]))

(deftest should-generate-calendar-file-correctly
  (file/generate "test-resources/file/FILE.hol" 2020 1 "test-resources/file/output")
  (= "20190728\n20200728\n20210728" (slurp "test-resources/file/output/FILE")))
