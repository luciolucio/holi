(ns gen-files
  (:require [com.piposaude.components.store.impl.s3-store :as store]
            [com.piposaude.calendars.file :as file]
            [tick.alpha.api :as t]
            [clojure.edn :as edn]))

(def bucket-name "pipo-holidays")

(defn -main [bracket-size]
  (let [store (store/map->S3Store {:settings {:s3-bucket bucket-name}})
        today (t/today)
        current-year (edn/read-string (t/format (tick.format/formatter "yyyy") today))
        directory (clojure.java.io/file "resources/calendars")
        [_ & files] (file-seq directory)]
    (run! #(file/generate! store (str %) current-year (edn/read-string bracket-size) today) files)))
