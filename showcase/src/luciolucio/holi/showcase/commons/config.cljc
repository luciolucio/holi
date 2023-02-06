(ns luciolucio.holi.showcase.commons.config
  #?(:cljs (:require-macros [luciolucio.holi.showcase.commons.config :as config])))

(def config
  "App configuration"
  #?(:cljs (config/get-config-map)
     :clj {}))

#?(:clj
   (defmacro ^:private get-config-map
     "Returns config map at compile time"
     []
     config))
