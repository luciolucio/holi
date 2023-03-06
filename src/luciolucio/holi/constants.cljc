(ns ^:no-doc luciolucio.holi.constants
  (:require [tick.core :as t]))

(def DATELIST-EXTENSION ".datelist")
(def WEEKEND-FILE-NAME "WEEKEND")
(def WEEKEND-FILE-NAMES {:sat-sun "WEEKEND-SAT-SUN"
                         :fri-sat "WEEKEND-FRI-SAT"})
(def ALL-WEEKEND-FILE-NAMES (set (conj (vals WEEKEND-FILE-NAMES) WEEKEND-FILE-NAME)))
(def PARSER-GRAMMAR-FILENAME "holidays.bnf")
(def MIN-YEAR 1900)
(def MAX-YEAR 5999)
(def TIMESTAMP-REFERENCE-DATE (t/date "1939-12-31"))
