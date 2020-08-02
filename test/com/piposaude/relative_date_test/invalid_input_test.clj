(ns com.piposaude.relative-date-test.invalid-input-test
  (:require [clojure.test :refer :all]
            [com.piposaude.relative-date-add :refer [relative-date-add]]
            [tick.core :as t]))

(deftest should-throw-on-illegal-n-argument
  (are [n]
    (is (thrown-with-msg? IllegalArgumentException #"Illegal n: .*" (relative-date-add (t/date "2020-07-29") n :days)))
    nil
    ""
    "3"
    5.1
    1e7
    :a
    {}
    #{}
    []))

(deftest should-throw-on-illegal-unit-argument
  (are [unit]
    (is (thrown-with-msg? IllegalArgumentException #"Unrecognized unit: .*" (relative-date-add (t/date "2020-07-29") 1 unit)))
    nil
    ""
    "3"
    5
    -11
    5.1
    1e7
    :z
    {}
    #{}
    []))

(deftest should-throw-on-illegal-date-argument
  (are [date]
    (is (thrown-with-msg? IllegalArgumentException #"Illegal date: .*" (relative-date-add date 1 :days)))
    nil
    ""
    "2020-11-10"
    5
    -11
    1.8
    :y
    []
    {}
    #{}))
