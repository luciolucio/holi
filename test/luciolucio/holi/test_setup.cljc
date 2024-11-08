(ns luciolucio.holi.test-setup
  (:require [clojure.test :as ct]
            [luciolucio.holi.core]
            [luciolucio.holi.util :as util :include-macros true]))

(def holiday-datelists
  {"WEEKEND-SAT-SUN"            (util/slurp-resource "TEST-WEEKEND-SAT-SUN.datelist")
   "WEEKEND-FRI-SAT"            (util/slurp-resource "TEST-WEEKEND-FRI-SAT.datelist")
   "DAY-THREE"                  (util/slurp-resource "DAY-THREE.datelist")
   "DAY-TWENTY-NINE"            (util/slurp-resource "DAY-TWENTY-NINE.datelist")
   "DAY-TWENTY-NINE-2021"       (util/slurp-resource "DAY-TWENTY-NINE-2021.datelist")
   "HOLIDAY-ON-FRI-SAT-WEEKEND" (util/slurp-resource "HOLIDAY-ON-FRI-SAT-WEEKEND.datelist")
   "HOLIDAY-ON-SAT-SUN-WEEKEND" (util/slurp-resource "HOLIDAY-ON-SAT-SUN-WEEKEND.datelist")
   "TEST-US"                    (util/slurp-resource "TEST-US.datelist")})

(defmacro with-test-calendars [& body]
  `(with-redefs [luciolucio.holi.core/holiday-datelists holiday-datelists]
     ~@body))

(defmacro defcalendartest [name & body]
 `(ct/deftest ~name (with-test-calendars ~@body)))
