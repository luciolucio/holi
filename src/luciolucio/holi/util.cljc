(ns ^:no-doc luciolucio.holi.util
  #?(:clj (:require [clojure.java.io :as io])))

#?(:clj
   (defmacro slurp-resource [file]
     (slurp (io/resource file))))
