(ns luciolucio.holi.test-setup
  (:require [luciolucio.holi.core]
            [luciolucio.holi.util :as util :include-macros true]))

(def holiday-strings
  {"WEEKEND"            (util/slurp-resource "TEST-WEEKEND.datelist")
   "DAY-THREE"          (util/slurp-resource "DAY-THREE.datelist")
   "DAY-TWENTY-NINE"    (util/slurp-resource "DAY-TWENTY-NINE.datelist")
   "HOLIDAY-ON-WEEKEND" (util/slurp-resource "HOLIDAY-ON-WEEKEND.datelist")})

(defn test-datelist-fixture [f]
  (with-redefs [luciolucio.holi.core/holiday-strings holiday-strings]
    (f)))
