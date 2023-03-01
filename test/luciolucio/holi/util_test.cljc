(ns luciolucio.holi.util-test
  (:require [clojure.test :as ct]
            [luciolucio.holi.util :as util]
            [tick.core :as t]))

(ct/deftest should-merge-correctly-when-merge-sorted-lists-with-two-lists
  (ct/are [a b expected]
          (= expected (util/merge-sorted-lists a b))
    [5 10 15] [2 3 20] [2 3 5 10 15 20]
    [] [1 4 9] [1 4 9]
    [1 4 9] [] [1 4 9]
    nil [1 4 9] [1 4 9]
    [1 4 9] nil [1 4 9]
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

(ct/deftest should-merge-correctly-when-merge-sorted-lists-with-two-lists-of-non-numbers
  (ct/are [a b expected]
          (= expected (util/merge-sorted-lists a b))
    ["A" "C"] ["B" "D"] ["A" "B" "C" "D"]
    [(t/date "2020-10-10") (t/date "2022-10-10")] [(t/date "2021-10-10") (t/date "2023-10-10")] [(t/date "2020-10-10") (t/date "2021-10-10") (t/date "2022-10-10") (t/date "2023-10-10")]))

(ct/deftest should-merge-a-large-list-when-merge-sorted-lists
  (let [full (vec (take 80000 (range)))
        first-half (subvec full 0 40000)
        second-half (subvec full 40000)]
    (ct/is (= full (util/merge-sorted-lists first-half second-half)))))

(ct/deftest should-merge-correctly-when-merge-sorted-lists-with-more-than-two-lists
  (ct/is (= [1 2 3 5 9 10 15 20 20] (util/merge-sorted-lists [5 10 15] [2 3 20] [1 9 20])))
  (ct/is (= [1 2 3 5 9 10 15 20 20] (util/merge-sorted-lists [5 10] [2 3 20] [1 9] [15 20])))
  (ct/is (= [0 1 3 9] (util/merge-sorted-lists [0 1 3] nil [9])))
  (ct/is (= [0 1 4] (util/merge-sorted-lists [0 1 4] [] nil)))
  (ct/is (= [] (util/merge-sorted-lists nil nil nil)))
  (ct/is (= [] (util/merge-sorted-lists nil nil [])))
  (ct/is (= [] (util/merge-sorted-lists [] [] []))))

(ct/deftest should-return-the-passed-list-or-empty-when-merge-sorted-lists-with-one-list
  (ct/is (= [5 10 15] (util/merge-sorted-lists [5 10 15])))
  (ct/is (= [5 10 15 20 40] (util/merge-sorted-lists [5 10 15 20 40])))
  (ct/is (= [40 20] (util/merge-sorted-lists [40 20])))
  (ct/is (= [] (util/merge-sorted-lists nil)))
  (ct/is (= [] (util/merge-sorted-lists []))))
