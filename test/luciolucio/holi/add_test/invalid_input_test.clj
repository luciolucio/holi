(ns luciolucio.holi.add-test.invalid-input-test
  (:require [clojure.test :refer :all]
            [luciolucio.holi :as holi]
            [tick.alpha.api :as t]))

(deftest should-throw-on-illegal-n-argument
  (are [n]
       (is (thrown-with-msg? IllegalArgumentException #"Illegal n: .*" (holi/add (t/date "2020-07-29") n :days)))
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
       (is (thrown-with-msg? IllegalArgumentException #"Unrecognized unit: .*" (holi/add (t/date "2020-07-29") 1 unit)))
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
       (is (thrown-with-msg? IllegalArgumentException #"Illegal date: .*" (holi/add date 1 :days)))
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
