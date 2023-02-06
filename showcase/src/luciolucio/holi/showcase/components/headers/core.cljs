(ns luciolucio.holi.showcase.components.headers.core
  (:require [luciolucio.holi.showcase.commons.style :as style]
            [luciolucio.holi.showcase.commons.svg :refer [embed-svg]]))

(def header-style
  (style/gen {:label           :header-style
              :paddingLeft     "10px"
              :display         "flex"
              :alignItems      "center"
              :backgroundColor "#fff"
              :height          "80px"
              :fontFamily      "monospace"}))

(defn header []
  [:header {:class header-style}
   [:a {:href "/#/"} (embed-svg "assets/svg/holi-showcase.svg")]])
