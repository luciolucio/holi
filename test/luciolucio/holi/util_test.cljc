(ns luciolucio.holi.util-test
  (:require [clojure.test :as ct]
            [luciolucio.holi.util :as util]))

(ct/deftest should-merge-sorted-lists-correctly
  (ct/are [a b expected]
          (= expected (util/merge-sorted-lists a b))
    [5 10 15] [2 3 20] [2 3 5 10 15 20]
    [] [1 4 9] [1 4 9]
    [1 4 9] [] [1 4 9]
    [0 0 0] [1 1 1] [0 0 0 1 1 1]
    [0 1 2] [3 4 5] [0 1 2 3 4 5]
    [3 4 5] [0 1 2] [0 1 2 3 4 5]
    [1] [1] [1 1]
    [1] [7] [1 7]
    [-5] [0] [-5 0]
    [] [] []
    nil [] []
    [] nil []
    nil nil []))
