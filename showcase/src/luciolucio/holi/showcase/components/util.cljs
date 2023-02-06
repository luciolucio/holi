(ns luciolucio.holi.showcase.components.util)

(defn with-keys [children]
  (doall
    (map-indexed
      (fn [i child]
        (if (satisfies? cljs.core.IWithMeta child)
          (vary-meta child update :key #(or % i))
          child))
      children)))
