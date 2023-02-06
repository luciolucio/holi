(ns luciolucio.holi.showcase.main.core
  (:require
   [luciolucio.holi.showcase.pages.home.core :as home]
   [reagent.dom :as r.dom]))

(defn init []
  (enable-console-print!)

  (r.dom/render [home/view] (.getElementById js/document "app")))
