(ns luciolucio.holi.showcase.routes.model-test
  (:require [luciolucio.holi.showcase.routes.model :as model]
            [luciolucio.holi.showcase.routes.core :as core]
            [cljs.test :refer [deftest is run-tests]]
            [reitit.core :as reitit]))

(deftest all-routes-have-views
  (let [all-matchable-routes (->> (reitit/route-names model/router)
                                  (map (comp :data #(reitit/match-by-name model/router %))))]

    (is (every? #(fn? (core/route-name->view (:name %))) all-matchable-routes)
        "Regression - There is a view for every leaf route or a container")))
