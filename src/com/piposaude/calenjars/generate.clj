(ns com.piposaude.calenjars.generate
  (:require [com.piposaude.calenjars.file :as file]
            [tick.alpha.api :as t]
            [clojure.edn :as edn]))

(defn -main [bracket-size calendar-file-dir output-path]
  (let [today (t/today)
        current-year (edn/read-string (t/format (tick.format/formatter "yyyy") today))
        directory (clojure.java.io/file calendar-file-dir)
        [_ & files] (file-seq directory)]
    (println "-----------------------------------------------------------")
    (println (format "Generating holidays for holiday files under %s" calendar-file-dir))
    (if (empty? files) (println "No holiday files to process"))
    (run! #(file/generate! (str %) output-path current-year (edn/read-string bracket-size)) files)
    (file/generate-weekend! output-path current-year (edn/read-string bracket-size))
    (println "Holiday generation finished")
    (println "-----------------------------------------------------------")))
