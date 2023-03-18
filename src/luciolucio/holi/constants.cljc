(ns ^:no-doc luciolucio.holi.constants
  (:require [tick.core :as t]))

(def DATELIST-EXTENSION ".datelist")
(def WEEKEND-FILE-NAME-PREFIX "WEEKEND")
(def WEEKEND-FILE-NAMES {:sat-sun (str WEEKEND-FILE-NAME-PREFIX "-SAT-SUN")
                         :fri-sat (str WEEKEND-FILE-NAME-PREFIX "-FRI-SAT")})
(def ALL-WEEKEND-OPTIONS (set (conj (keys WEEKEND-FILE-NAMES))))
(def ALL-WEEKEND-FILE-NAMES (set (vals WEEKEND-FILE-NAMES)))
(def PARSER-GRAMMAR-FILENAME "holidays.bnf")
(def MIN-YEAR 1900)
(def MAX-YEAR 5999)
(def TIMESTAMP-REFERENCE-DATE (t/date "1939-12-31"))
