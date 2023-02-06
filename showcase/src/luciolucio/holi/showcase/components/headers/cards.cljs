(ns luciolucio.holi.showcase.components.headers.cards
  (:require [nubank.workspaces.core :as ws]
            [nubank.workspaces.model :as wsm]
            [nubank.workspaces.card-types.react :as ct.react]
            [luciolucio.holi.showcase.components.headers.core :refer [header]]
            [reagent.core :as r]))

(ws/defcard header-card
            {::wsm/card-width 10
             ::wsm/card-height 4
             ::wsm/align {:flex 1
                          :display "flex"
                          :flex-direction "column"}}
            (ct.react/react-card
              (r/as-element [header "Entrar"])))
