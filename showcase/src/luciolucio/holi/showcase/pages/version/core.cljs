(ns luciolucio.holi.showcase.pages.version.core
  (:require [luciolucio.holi.showcase.commons.config :as config]))

(defn view [_match]
  [:<>
   [:h2 "Version"]
   [:p (:version config/config)]
   [:a {:href "/#/"} "Home"]])
