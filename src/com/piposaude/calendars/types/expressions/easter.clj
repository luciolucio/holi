(ns com.piposaude.calendars.types.expressions.easter
  (:require [com.piposaude.calendars.types.common :refer [holiday]]
            [tick.core :as t]))

(def year->easter-holiday
  {2012 {:name "Easter" :date (t/date "2012-04-08")}
   2013 {:name "Easter" :date (t/date "2013-03-31")}
   2014 {:name "Easter" :date (t/date "2014-04-20")}
   2015 {:name "Easter" :date (t/date "2015-04-05")}
   2016 {:name "Easter" :date (t/date "2016-03-27")}
   2017 {:name "Easter" :date (t/date "2017-04-16")}
   2018 {:name "Easter" :date (t/date "2018-04-01")}})

(defn get-holiday-easter [year name operator operand]
  (get year->easter-holiday year))
