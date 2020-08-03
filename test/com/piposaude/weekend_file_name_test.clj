(ns com.piposaude.weekend-file-name-test
  (:require [clojure.test :refer :all]
            [com.piposaude.calenjars.file :as file]
            [com.piposaude.relative-date-add :as rdate]))

(deftest weekend-file-name-should-be-the-same-between-generator-and-relative-date-add
  (is (= file/WEEKEND-FILE-NAME rdate/WEEKEND-FILE-NAME)))
