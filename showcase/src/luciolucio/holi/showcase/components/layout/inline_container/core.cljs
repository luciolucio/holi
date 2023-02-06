(ns luciolucio.holi.showcase.components.layout.inline-container.core
  (:require [luciolucio.holi.showcase.components.util :refer [with-keys]]
            [luciolucio.holi.showcase.commons.style :as style]))

(defn gen-inline-style
  [spacing align justify]
  (style/gen {:label                    :inline-container-style
              :display                  "flex"
              :flexDirection            "row"
              :justifyContent           justify
              :alignItems               align
              "& > *:not(:first-child)" {:marginLeft spacing}}))

(defn inline-container
  [{:keys [spacing align justify] :or {align "center" justify "flex-start"}} & children]
  (into [:div {:class (gen-inline-style spacing align justify)}]
        (with-keys children)))
