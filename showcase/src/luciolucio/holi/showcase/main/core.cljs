(ns luciolucio.holi.showcase.main.core
  (:require [re-frame.core :as rf]
            [reagent.dom :as r.dom]
            [luciolucio.holi.showcase.routes.core :as routes]
            [luciolucio.holi.showcase.routes.model :as routes.model]
            [luciolucio.holi.showcase.main.model :as main.model]))

(defn main-container []
  [routes/container])

(defn init []
  (enable-console-print!)

  (rf/dispatch-sync [::main.model/load-app])
  (routes.model/track-routes)
  (r.dom/render [main-container] (.getElementById js/document "app")))
