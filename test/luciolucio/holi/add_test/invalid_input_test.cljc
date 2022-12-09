(ns luciolucio.holi.add-test.invalid-input-test
  (:require [clojure.test :as ct]
            [luciolucio.holi :as holi]
            [tick.core :as t])
  #?(:clj
     (:import (clojure.lang ExceptionInfo))))

(ct/deftest should-throw-on-illegal-n-argument
  (ct/are [n]
          (ct/is (thrown-with-msg? #?(:clj ExceptionInfo :cljs js/Error) #"Illegal n: .*" (holi/add (t/date "2020-07-29") n :days)))
    nil
    ""
    "3"
    5.1
    1e7
    :a
    {}
    #{}
    []))

(ct/deftest should-throw-on-illegal-unit-argument
  (ct/are [unit]
          (ct/is (thrown-with-msg? #?(:clj ExceptionInfo :cljs js/Error) #"Unrecognized unit: .*" (holi/add (t/date "2020-07-29") 1 unit)))
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

(ct/deftest should-throw-on-illegal-date-argument
  (ct/are [date]
          (ct/is (thrown-with-msg? #?(:clj ExceptionInfo :cljs js/Error) #"Illegal date: .*" (holi/add date 1 :days)))
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
