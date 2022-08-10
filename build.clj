(ns build
  (:require [clojure.tools.build.api :as b]))

(def version "0.9.0")

(def lib 'io.github.luciolucio/holi)
(def basis (b/create-basis {:project "deps.edn"}))

(defn clean [_]
  (b/delete {:path "target"})
  (b/delete {:path "resources/calendars-generated"}))

(defn jar [{:keys [build-root jar-file]}]
  (let [class-dir (str build-root "/" "classes")]
    (b/write-pom {:class-dir class-dir
                  :lib       lib
                  :version   version
                  :basis     basis
                  :src-dirs  ["src"]})
    (b/copy-dir {:src-dirs   ["src" "resources/calendars-generated"]
                 :target-dir class-dir})
    (b/jar {:class-dir class-dir
            :jar-file  (str build-root "/" jar-file)})))
