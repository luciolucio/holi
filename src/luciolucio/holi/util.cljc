(ns ^:no-doc luciolucio.holi.util
  #?(:clj (:require [clojure.java.io :as io])))

#?(:clj
   (defmacro slurp-resource [file]
     (slurp (io/resource file))))

(defn merge-sorted-lists
  "Returns a sorted list created by merging lists that are themselves already sorted"
  ([list1]
   (or list1 []))
  ([list1 list2]
   (loop [result []
          remaining1 list1
          remaining2 list2]
     (cond
       (and (empty? remaining1) (empty? remaining2)) result
       (empty? remaining1) (concat result remaining2)
       (empty? remaining2) (concat result remaining1)
       :else
       (let [val1 (first remaining1)
             val2 (first remaining2)]
         (if (neg? (compare val1 val2))
           (recur (conj result val1) (rest remaining1) remaining2)
           (recur (conj result val2) remaining1 (rest remaining2)))))))
  ([list1 list2 & lists]
   (reduce merge-sorted-lists (merge-sorted-lists list1 list2) lists)))
