(ns ^:no-doc luciolucio.holi.util
  #?(:clj (:require [clojure.java.io :as io])))

#?(:clj
   (defmacro slurp-resource [file]
     (slurp (io/resource file))))

(defn merge-sorted-lists [a b]
  (cond
    (empty? a)
    (or b [])

    (empty? b)
    (or a [])

    (< (first a) (first b))
    (concat [(first a)] (merge-sorted-lists (rest a) b))

    :else
    (concat [(first b)] (merge-sorted-lists a (rest b)))))
