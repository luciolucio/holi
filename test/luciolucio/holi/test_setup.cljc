(ns luciolucio.holi.test-setup
  (:require [luciolucio.holi.core]
            [luciolucio.holi.util :as util :include-macros true]))

(def holiday-datelists
  {"WEEKEND"              (util/slurp-resource "TEST-WEEKEND-SAT-SUN.datelist")
   "WEEKEND-SAT-SUN"      (util/slurp-resource "TEST-WEEKEND-SAT-SUN.datelist")
   "DAY-THREE"            (util/slurp-resource "DAY-THREE.datelist")
   "DAY-TWENTY-NINE"      (util/slurp-resource "DAY-TWENTY-NINE.datelist")
   "DAY-TWENTY-NINE-2021" (util/slurp-resource "DAY-TWENTY-NINE-2021.datelist")
   "HOLIDAY-ON-WEEKEND"   (util/slurp-resource "HOLIDAY-ON-WEEKEND.datelist")
   "TEST-US"              (util/slurp-resource "TEST-US.datelist")})

(defn test-datelist-fixture [f]
  (with-redefs [luciolucio.holi.core/holiday-datelists holiday-datelists]
    (f)))
