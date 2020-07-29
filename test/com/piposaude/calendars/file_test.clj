(ns com.piposaude.calendars.file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.file :as file]
            [com.stuartsierra.component :as component]
            [com.piposaude.components.store.impl.in-memory-store :as store]
            [com.piposaude.components.store.api :as store.api]))

(def store (component/start (store/map->InMemoryStore {:settings {}})))

(deftest should-generate-calendar-file-when-generate
  (file/generate store "test-resources/file/FILE.hol" 2020 1 "file/output")
  (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object store "file/output/FILE"))))))

(deftest should-generate-calendar-file-with-larger-bracket-when-generate
  (file/generate store "test-resources/file/FILE.hol" 2020 2 "file/output")
  (is (= "20180728\n20190728\n20200728\n20210728\n20220728" (slurp (:input-stream (store.api/fetch-object store "file/output/FILE"))))))
