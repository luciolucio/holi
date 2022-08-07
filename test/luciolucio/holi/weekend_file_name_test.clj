(ns luciolucio.holi.weekend-file-name-test
  (:require [clojure.test :refer :all]
            [luciolucio.holi.file :as file]
            [luciolucio.holi :as holi]))

(deftest weekend-file-name-should-be-the-same-between-generator-and-calendar-namespaces
  (is (= file/WEEKEND-FILE-NAME holi/WEEKEND-FILE-NAME)))
