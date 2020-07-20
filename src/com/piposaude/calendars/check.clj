(ns com.piposaude.calendars.check
  (:require [clojure.string :as str]))

(defn get-holiday-file-errors [filename]
  (with-open [reader (clojure.java.io/reader filename)]
    (let [lines (line-seq reader)
          line-count (count lines)]
      (cond (<= line-count 0) "Holiday file cannot be empty"
            (some str/blank? lines) "Holiday file cannot have blank lines"
            :else nil))))
