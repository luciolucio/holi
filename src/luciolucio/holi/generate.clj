(ns luciolucio.holi.generate
  (:require [luciolucio.holi.file :as file]
            [tick.alpha.api :as t]
            [clojure.edn :as edn])
  (:import (java.time LocalDate)
           (clojure.lang ExceptionInfo)))

(defn -main [bracket calendar-file-dir output-path]
  (let [^LocalDate today (t/today)
        current-year (.getYear today)
        directory (clojure.java.io/file calendar-file-dir)
        [_ & files] (file-seq directory)
        bracket-size (edn/read-string bracket)]
    (println "-----------------------------------------------------------")
    (println (format "Generating holidays for holiday files under %s" calendar-file-dir))
    (when (empty? files) (println "No holiday files to process"))
    (try
      (run! #(file/generate-datelist! calendar-file-dir (str %) output-path current-year bracket-size) files)
      (catch ExceptionInfo e
        (if (= (:error (ex-data e)) :weekend-dot-hol-present)
          (do
            (println "ERROR: WEEKEND.hol is not an allowed file name, please use another name")
            (System/exit 1))
          (throw e))))
    (file/generate-weekend-datelist! output-path current-year bracket-size)
    (println "Holiday generation finished")
    (println "-----------------------------------------------------------")))
