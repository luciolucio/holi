(ns com.piposaude.calendars.check
  (:require [clojure.string :as str]))

(defn- blank-lines? [lines]
  (or (empty? lines) (some str/blank? lines)))

(defn- too-many-pipes? [index line]
  (when (> (get (frequencies line) \|) 1) index))

(defn- get-first-line-with-too-many-pipes [lines]
  (+ 1 (first (keep-indexed too-many-pipes? lines))))

(defn get-holiday-file-errors [filename]
  (with-open [reader (clojure.java.io/reader filename)]
    (let [lines (line-seq reader)]
      (if (blank-lines? lines)
        (cond (empty? lines) "Holiday file cannot be empty"
              (some str/blank? lines) "Holiday file cannot have blank lines")
        (let [first-line-with-too-many-pipes (get-first-line-with-too-many-pipes lines)]
          (cond
            first-line-with-too-many-pipes (format "Line %d: holiday definition cannot have two pipes" first-line-with-too-many-pipes)
            :default nil))))))
