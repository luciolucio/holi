(ns luciolucio.holi.core-test
  (:require [clojure.test :as ct]
            [luciolucio.holi.core :as core]))

(ct/deftest should-return-vector-when-read-dates
  (ct/is (vector? (core/read-dates "BR")))
  (ct/is (vector? (core/read-dates ["BR" "US"]))))
