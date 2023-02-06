(ns luciolucio.holi.showcase.components.layout.stack-container.core
  (:require [luciolucio.holi.showcase.components.util :refer [with-keys]]
            [luciolucio.holi.showcase.commons.style :as style]))

(defn gen-stack-style
  [spacing justify align]
  (style/gen {:label                    :stack-container-style
              :display                  "flex"
              :flexDirection            "column"
              :alignItems               align
              :justifyContent           justify
              "& > *:not(:first-child)" {:marginTop spacing}}))

(defn stack-container
  [{:keys [spacing justify align] :or {justify "flex-start" align "stretch"}} & children]
  (into [:div {:class (gen-stack-style spacing justify align)}]
        (with-keys children)))
