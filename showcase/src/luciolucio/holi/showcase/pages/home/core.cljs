(ns luciolucio.holi.showcase.pages.home.core
  (:require [luciolucio.holi.showcase.commons.config :as config]
            [luciolucio.holi.showcase.commons.style :as style]
            [luciolucio.holi.showcase.components.layout.stack-container.core :refer [stack-container]]
            [luciolucio.holi.showcase.components.layout.inline-container.core :refer [inline-container]]))

(def version-style (style/gen {:label      :version-style
                               :color      "grey"
                               :marginLeft "1em"}))

(defn version [v]
  [:span {:class version-style} v])

(def horizontal-line-style (style/gen {:height          "1px"
                                       :width           "100%"
                                       :backgroundColor "black"}))

(defn horizontal-line []
  [:div {:class horizontal-line-style :role "separator"}])

(def header-style (style/gen {:label   :header-style
                              :padding "1em 1em"}))

(defn sidebar-and-main []
  [inline-container {:spacing "0em"}
   [stack-container {:spacing "1em"}
    [:p "Countries"]
    [:p "Cities"]]
   [:p "Main"]])

(defn header []
  [:div {:class header-style}
   [inline-container {:spacing "0em" :justify "space-between"}
    [:div "Holi calendar showcase" [version (:version config/config)]]
    [:a {:href "https://cljdoc.org/d/io.github.luciolucio/holi/CURRENT"} "Docs"]]])

(defn view [_match]
  [stack-container {:spacing "0em"}
   [header]
   [horizontal-line]
   [sidebar-and-main]])
