(ns luciolucio.holi.generate
  (:require [luciolucio.holi.file :as file]
            [tick.alpha.api :as t]
            [clojure.edn :as edn])
  (:import (java.time LocalDate)))

(defn -main [bracket calendar-file-dir output-path]
  (let [^LocalDate today (t/today)
        current-year (.getYear today)
        directory (clojure.java.io/file calendar-file-dir)
        [_ & files] (file-seq directory)
        bracket-size (edn/read-string bracket)]
    (println "-----------------------------------------------------------")
    (println (format "Generating holidays for holiday files under %s" calendar-file-dir))
    (if (empty? files) (println "No holiday files to process"))
    (run! #(file/generate-datelist! (str %) output-path current-year bracket-size) files)
    (file/generate-weekend-datelist! output-path current-year bracket-size)
    (println "Holiday generation finished")
    (println "-----------------------------------------------------------")))
