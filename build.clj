(ns build
  (:require [clojure.tools.build.api :as b]))

(def version "0.13.1")

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
                  :scm       {:url                 "https://github.com/luciolucio/holi"
                              :connection          "scm:git:git://github.com/luciolucio/holi.git"
                              :developerConnection "scm:git:ssh://git@github.com/luciolucio/holi.git"
                              :tag                 version}
                  :src-dirs  ["src"]})
    (b/copy-dir {:src-dirs   ["src"]
                 :target-dir class-dir})
    (b/copy-dir {:src-dirs   ["resources/calendars-generated"]
                 :target-dir (str class-dir "/" "calendars-generated")})
    (b/jar {:class-dir class-dir
            :jar-file  (str build-root "/" jar-file)})))
