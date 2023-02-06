(ns luciolucio.holi.showcase.main.model
  (:require [re-frame.core :as rf]))

(rf/reg-event-db
  ::load-app
  (fn [db _]
    (assoc db :app-loaded true)))

(rf/reg-sub
  ::app-loaded
  (fn [db _]
    (:app-loaded db)))
