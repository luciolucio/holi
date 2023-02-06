(ns luciolucio.holi.showcase.commons.svg
  (:require [pl.danieljanus.tagsoup :as tagsoup]
            [clojure.walk :as walk]
            [clojure.java.io :as io]))

(defmacro embed-svg [svg-resource-path]
  (let [hiccup (->> (io/resource svg-resource-path)
                    slurp
                    tagsoup/parse-string
                    (walk/postwalk-replace {; SVG
                                            :viewbox       :viewBox
                                            :xmlspace      :xmlSpace
                                            :xmlnsxlink    :xmlnsXlink
                                            ; Masks
                                            :maskunits     :maskUnits
                                            :filterunits   :filterUnits
                                            ; Animate Transform
                                            :attributename :attributeName
                                            :attributetype :attributeType
                                            :repeatcount   :repeatCount}))]
    `~hiccup))
