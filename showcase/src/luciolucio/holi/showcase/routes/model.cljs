(ns luciolucio.holi.showcase.routes.model
  (:require [reitit.frontend :as reitit.frontend]
            [reitit.frontend.easy :as reitit.easy]
            [reitit.coercion.spec :as reitit.spec]
            [reitit.frontend.controllers :as reitit.controllers]
            [reagent.core :as r]))

(defonce match (r/atom nil))

(def routes
  [["" {}]

   ["/"
    {:name  :routes/home
     :title "Showcase of holi's calendars"}]])

(def router (reitit.frontend/router routes {:data {:coercion reitit.spec/coercion}}))

(defn track-routes
  []
  (reitit.easy/start!
    router
    (fn [new-match]
      (let [title (get-in new-match [:data :title])]
        (set! js/document.title title)
        (swap! match
               (fn [old-match]
                 (when new-match
                   (assoc new-match
                     :controllers (reitit.controllers/apply-controllers (:controllers old-match) new-match)))))))
    {:use-fragment true}))
