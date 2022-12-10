(ns luciolucio.holi.test-setup
  (:require [shadow.resource :as rc]
            [luciolucio.holi.core]))

(def holiday-strings
  {"WEEKEND"            (rc/inline "WEEKEND.datelist")
   "DAY-THREE"          (rc/inline "DAY-THREE.datelist")
   "DAY-TWENTY-NINE"    (rc/inline "DAY-TWENTY-NINE.datelist")
   "HOLIDAY-ON-WEEKEND" (rc/inline "HOLIDAY-ON-WEEKEND.datelist")})

(defn test-datelist-fixture [f]
  (with-redefs [luciolucio.holi.core/holiday-strings holiday-strings]
    (f)))
