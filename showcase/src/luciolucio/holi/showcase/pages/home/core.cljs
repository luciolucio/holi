(ns luciolucio.holi.showcase.pages.home.core
  (:require [luciolucio.holi.showcase.commons.style :as style]
            [luciolucio.holi.showcase.components.layout.stack-container.core :refer [stack-container]]
            [luciolucio.holi.showcase.components.layout.inline-container.core :refer [inline-container]]
            [luciolucio.holi :as holi]
            [goog.string :as gstr]
            goog.string.format
            [reagent.core :as r]
            [tick.core :as t]
            [tick.locale-en-us]))

(def container-style
  (style/gen {:label         :route-container-style
              :display       "flex"
              :flexDirection "column"
              :alignItems    "stretch"
              :position      "relative"
              :minWidth      "100%"
              :minHeight     "100vh"}))

(def version-style (style/gen {:label      :version-style
                               :color      "grey"
                               :marginLeft "1em"}))

(defn version [v]
  [:span {:class version-style} v])

(def horizontal-line-style (style/gen {:height          "1px"
                                       :width           "100%"
                                       :backgroundColor "black"}))

(defn horizontal-line []
  [:div {:class horizontal-line-style :role "separator"}])

(def header-style (style/gen {:label   :header-style
                              :padding "1em 1em"}))

(def sidebar-and-main-style
  (style/gen {:label              :sidebar-and-main-container-style
              :height             "100vh"
              :padding            "0em 1em"
              :display            :flex
              :flexDirection      :row
              "& > *:first-child" {:flexShrink  0
                                   :borderRight "1px solid black"
                                   :width       "25%"
                                   :marginRight "1em"}
              "& > *:last-child"  {:flexGrow  1
                                   :textAlign :center}}))

(defn sidebar [change-calendar]
  (letfn [(calendar-button [calendar description]
            [:a {:on-click #(change-calendar calendar description)} (gstr/format "%s (%s)" description calendar)])]
    [stack-container {:spacing "1em"}
     [:h2 "Countries"]
     [calendar-button "BR" "Brazil"]
     [calendar-button "GB" "Great Britain"]
     [calendar-button "US" "United States"]
     [:h2 "Cities"]
     [calendar-button "brazil/sao-paulo" "São Paulo"]]))

(defn holiday [h]
  [:p (gstr/format "%s - %s" (t/format "dd/MMM/YYYY" (:date h)) (:name h))])

(defn main-calendar-view [year next-year previous-year calendar description]
  [:div
   [:h1 (gstr/format "%s Holiday Calendar (%s)" calendar description)]
   [inline-container {:spacing "1em" :justify :center}
    [:button {:on-click #(previous-year)} "<"]
    [:span (str year)]
    [:button {:on-click #(next-year)} ">"]]
   (into [:<>]
         (mapv holiday (holi/list-holidays year calendar)))])

(defn sidebar-and-main [calendar-info year]
  (let [[calendar description] @calendar-info
        change-calendar (fn [calendar description] (reset! calendar-info [calendar description]))
        selected-year @year
        next-year #(swap! year inc)
        previous-year #(swap! year dec)]
    [:div {:class sidebar-and-main-style}
     [sidebar change-calendar]
     [main-calendar-view selected-year next-year previous-year calendar description]]))

(defn header []
  [:div {:class header-style}
   [inline-container {:spacing "0em" :justify "space-between"}
    [:div "Holi calendar showcase" [version "0.14.0"]]
    [:a {:href "https://cljdoc.org/d/io.github.luciolucio/holi/CURRENT"} "Docs"]]])

(defn view []
  [:div {:class container-style}
   [stack-container {:spacing "0em"}
    [header]
    [horizontal-line]
    [sidebar-and-main (r/atom ["US" "United States"]) (r/atom (-> (t/year) int))]]])
