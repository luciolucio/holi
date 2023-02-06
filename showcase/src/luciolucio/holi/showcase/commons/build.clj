(ns luciolucio.holi.showcase.commons.build
  (:require [clojure.set :refer [union]]
            [clojure.java.io :as io]
            [luciolucio.holi.showcase.commons.config :as config]
            [aero.core :as aero]))

(defn deep-merge [& maps]
  (apply merge-with (fn [& args]
                      (cond
                        (every? map? args) (apply deep-merge args)
                        (every? set? args) (apply union args)
                        :else              (last args)))
         maps))

(defmethod aero/reader 'deep-merge [_ _ values]
  (apply deep-merge values))

(defn read-config [profile]
  (-> "config.edn"
      io/resource
      (aero/read-config {:profile profile})
      (assoc :profile profile)))

(defn load-config
  {:shadow.build/stage :compile-prepare}
  [{:keys [shadow.build/config]
    :as   build-state}]
  (let [app-config (read-config (-> config
                                    :profile
                                    keyword))]
    (alter-var-root #'config/config (constantly app-config))
    (-> build-state
        (assoc-in [:compiler-options :external-config :profile] app-config))))
