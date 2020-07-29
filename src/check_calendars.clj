(ns check-calendars
  (:require [com.piposaude.calendars.file :as file]
            [clojure.tools.logging :as log]
            [com.piposaude.calendars.check :as check]))

(defn check-file [filename]
  (if (check/valid-holiday-file? filename)
    nil
    [filename (check/get-errors filename)]))

(defn log-errors [[filename errors]]
  (println "Errors for" filename)
  (clojure.pprint/pprint errors)
  (println))

(defn -main []
  (let [directory (clojure.java.io/file "resources/calendars")
        [_ & files] (file-seq directory)]
    (log/info "-----------------------------------------------------------")
    (log/info "Running check files for holidays under resources/calendars")
    (if (empty? files) (log/warn "No holiday files to process"))
    (let [errors (keep #(check-file (str %)) files)]
      (if (seq errors)
        (do
          (run! log-errors errors)
          (throw (ex-info "One or more holiday files have errors" {})))
        (log/info "All holiday files formatted correctly")))
    (log/info "Check files finished")
    (log/info "-----------------------------------------------------------")))
