(ns com.piposaude.calendars.file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.file :as file]
            [com.stuartsierra.component :as component]
            [com.piposaude.components.store.impl.in-memory-store :as inmm]
            [com.piposaude.components.store.api :as store.api]))

(deftest should-generate-calendar-file-correctly
  (let [store (component/start (inmm/map->InMemoryStore {:settings {:base-dir "test-resources"}}))]
    (file/generate store "test-resources/file/FILE.hol" 2020 1 "file/output")
    (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object store "file/output/FILE")))))))
