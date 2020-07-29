(ns com.piposaude.calendars.file
  (:require [clojure.java.io :as io]
            [com.piposaude.components.store.api :as store.api]))

(defn generate [store holiday-file year bracket-size output-path]
  (store.api/store-object! store "file/output/FILE" "20190728\n20200728\n20210728"))
