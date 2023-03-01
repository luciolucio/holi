(ns ^:no-doc luciolucio.holi.util
  #?(:clj (:require [clojure.java.io :as io])))

#?(:clj
   (defmacro slurp-resource [file]
     (slurp (io/resource file))))

(defn merge-sorted-lists
  "Returns a sorted list created by merging lists that are themselves already sorted"
  ([x y]
   (cond
     (empty? x)
     (or y [])

     (empty? y)
     (or x [])

     (< (first x) (first y))
     (concat [(first x)] (merge-sorted-lists (rest x) y))

     :else
     (concat [(first y)] (merge-sorted-lists x (rest y)))))
  ([x y & zs]
   (reduce merge-sorted-lists (merge-sorted-lists x y) zs)))
