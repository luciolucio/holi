(ns com.piposaude.weekend-file-name-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calenjars.file :as file]
            [com.piposaude.calenjars :as calendar]))

(deftest weekend-file-name-should-be-the-same-between-generator-and-calendar-namespaces
  (is (= file/WEEKEND-FILE-NAME calendar/WEEKEND-FILE-NAME)))
