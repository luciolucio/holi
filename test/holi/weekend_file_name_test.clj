(ns holi.weekend-file-name-test
  (:require [clojure.test :refer :all]
            [holi.file :as file]
            [holi.core :as holi]))

(deftest weekend-file-name-should-be-the-same-between-generator-and-calendar-namespaces
  (is (= file/WEEKEND-FILE-NAME holi/WEEKEND-FILE-NAME)))
