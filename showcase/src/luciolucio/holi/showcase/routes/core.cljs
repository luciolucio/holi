(ns luciolucio.holi.showcase.routes.core
  (:require [re-frame.core :as rf]
            [luciolucio.holi.showcase.commons.style :as style]
            [luciolucio.holi.showcase.components.loading.core :refer [loading-view]]
            [luciolucio.holi.showcase.routes.model :as model]
            [luciolucio.holi.showcase.pages.home.core :as home]
            [luciolucio.holi.showcase.main.model :as main.model]))

(def routes-container-style
  (style/gen {:label         :route-container-style
              :display       "flex"
              :flexDirection "column"
              :alignItems    "stretch"
              :position      "relative"
              :minWidth      "100%"
              :minHeight     "100vh"}))

(def route-name->view
  {:routes/home home/view})

(defn container []
  (fn []
    (let [app-loaded? @(rf/subscribe [::main.model/app-loaded])]
      (if app-loaded?
        [:div {:class routes-container-style}
         (if @model/match
           (let [{:keys [name]} (:data @model/match)]
             [(route-name->view name) @model/match])
           "Page Not found")]
        [loading-view]))))
