(ns com.piposaude.calendars.file-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calendars.file :as file]
            [com.stuartsierra.component :as component]
            [com.piposaude.components.store.impl.in-memory-store :as store]
            [com.piposaude.components.store.api :as store.api]
            [tick.core :as t]))

(def ^:dynamic *store* {})

(defn setup [f]
  (binding [*store* (component/start (store/map->InMemoryStore {:settings {}}))]
    (f)))

(use-fixtures :each setup)

(deftest should-generate-calendar-file-when-generate
  (file/generate! *store* "test-resources/file/FILE.hol" 2020 1 (t/today))
  (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object *store* "FILE"))))))

(deftest should-generate-calendar-file-when-generate-with-larger-bracket
  (file/generate! *store* "test-resources/file/FILE.hol" 2020 2 (t/today))
  (is (= "20180728\n20190728\n20200728\n20210728\n20220728" (slurp (:input-stream (store.api/fetch-object *store* "FILE"))))))

(deftest should-generate-calendar-file-in-sorted-order-when-generate-with-include
  (file/generate! *store* "test-resources/file/INCLUDE.hol" 2020 1 (t/today))
  (is (= "20190103\n20190728\n20200103\n20200728\n20210103\n20210728" (slurp (:input-stream (store.api/fetch-object *store* "INCLUDE"))))))

(deftest should-move-existing-calendar-file-when-generate-with-existing-file
  (store.api/store-object! *store* "FILE" "anything")

  (file/generate! *store* "test-resources/file/FILE.hol" 2020 1 (t/date "2030-01-10"))
  (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object *store* "FILE")))))
  (is (= "anything" (slurp (:input-stream (store.api/fetch-object *store* "FILE-UNTIL-20300110")))))

  (file/generate! *store* "test-resources/file/FILE.hol" 2020 2 (t/date "2030-01-11"))
  (is (= "20180728\n20190728\n20200728\n20210728\n20220728" (slurp (:input-stream (store.api/fetch-object *store* "FILE")))))
  (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object *store* "FILE-UNTIL-20300111")))))
  (is (= "anything" (slurp (:input-stream (store.api/fetch-object *store* "FILE-UNTIL-20300110"))))))

(deftest should-not-create-archive-when-generate-with-holiday-not-changed
  (store.api/store-object! *store* "FILE" "20190728\n20200728\n20210728")
  (file/generate! *store* "test-resources/file/FILE.hol" 2020 1 (t/date "2020-04-10"))
  (is (= "20190728\n20200728\n20210728" (slurp (:input-stream (store.api/fetch-object *store* "FILE")))))
  (is (not (store.api/does-object-exist? *store* "FILE-UNTIL-20200410"))))
