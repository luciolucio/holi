(ns luciolucio.holi.showcase.commons.style
  (:require ["emotion" :as emotion]))

(def gen (comp emotion/css clj->js))
