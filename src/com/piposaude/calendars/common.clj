(ns com.piposaude.calendars.common
  (:import (java.nio.file Paths)))

(defn drop-include [result]
  (let [[type & _] (first result)]
    (if (= :include type)
      (rest result)
      result)))

(defn holiday-was-included? [result]
  (let [[type & _] (first result)]
    (= :include type)))

(defn included-filename [including-file included-holiday-name]
  (let [including-file-path (.toString (.getParent (Paths/get including-file (into-array String []))))]
    (.toString (Paths/get including-file-path (into-array String [(str included-holiday-name ".hol")])))))
