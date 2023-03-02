(ns ^:no-doc luciolucio.holi.util
  #?(:clj (:require [clojure.java.io :as io])))

#?(:clj
   (defmacro slurp-resource [file]
     (slurp (io/resource file))))

(defn merge-sorted-collections
  "Returns a sorted vector created by merging collections that are themselves already sorted"
  ([coll1]
   (or (vec coll1) []))
  ([coll1 coll2]
   (loop [result []
          remaining1 coll1
          remaining2 coll2]
     (cond
       (and (empty? remaining1) (empty? remaining2)) result
       (empty? remaining1) (vec (concat result remaining2))
       (empty? remaining2) (vec (concat result remaining1))
       :else
       (let [val1 (first remaining1)
             val2 (first remaining2)]
         (if (neg? (compare val1 val2))
           (recur (conj result val1) (rest remaining1) remaining2)
           (recur (conj result val2) remaining1 (rest remaining2)))))))
  ([coll1 coll2 & colls]
   (reduce merge-sorted-collections (merge-sorted-collections coll1 coll2) colls)))
