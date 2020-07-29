(ns com.piposaude.calendars.file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.file :as file]
            [com.stuartsierra.component :as component]
            [com.piposaude.components.store.impl.in-memory-store :as store]
            [com.piposaude.components.store.api :as store.api]
            [tick.core :as t]))

(def store (component/start (store/map->InMemoryStore {:settings {}})))

(deftest should-generate-calendar-file-when-generate
  (file/generate! store "test-resources/file/FILE.hol" 2020 1 (t/today))
  (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object store "FILE"))))))

(deftest should-generate-calendar-file-when-generate-with-larger-bracket
  (file/generate! store "test-resources/file/FILE.hol" 2020 2 (t/today))
  (is (= "20180728\n20190728\n20200728\n20210728\n20220728" (slurp (:input-stream (store.api/fetch-object store "FILE"))))))

(deftest should-generate-calendar-file-in-sorted-order-when-generate-with-include
  (file/generate! store "test-resources/file/INCLUDE.hol" 2020 1 (t/today))
  (is (= "20190103\n20190728\n20200103\n20200728\n20210103\n20210728" (slurp (:input-stream (store.api/fetch-object store "INCLUDE"))))))

(deftest should-move-existing-calendar-file-when-generate-with-existing-file
  (store.api/store-object! store "FILE" "anything")

  (file/generate! store "test-resources/file/FILE.hol" 2020 1 (t/date "2020-07-29"))
  (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object store "FILE")))))
  (is (= "anything" (slurp (:input-stream (store.api/fetch-object store "FILE-UNTIL-20200729")))))

  (file/generate! store "test-resources/file/FILE.hol" 2020 2 (t/date "2020-07-30"))
  (is (= "20180728\n20190728\n20200728\n20210728\n20220728" (slurp (:input-stream (store.api/fetch-object store "FILE")))))
  (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object store "FILE-UNTIL-20200730")))))
  (is (= "anything" (slurp (:input-stream (store.api/fetch-object store "FILE-UNTIL-20200729"))))))
