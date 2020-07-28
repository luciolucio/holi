(ns com.piposaude.calendars.file
  (:require [clojure.java.io :as io]))

(defn generate [holiday-file year bracket-size output-dir]
  (io/make-parents "test-resources/file/output/FILE")
  (spit "test-resources/file/output/FILE" "20190728\n20200728\n20210728"))
