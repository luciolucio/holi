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

(def SEPARATOR-COLOR "#C0C0C0")
(def KEYCODE-ENTER 13)

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
                                       :backgroundColor SEPARATOR-COLOR}))

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
                                   :borderRight (str "1px solid " SEPARATOR-COLOR)
                                   :width       "25%"
                                   :marginRight "1em"}
              "& > *:last-child"  {:flexGrow  1
                                   :textAlign :center}}))

(def year-field-style
  (style/gen {:label :year-field-style
              :width "6ch"}))

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

(defn change-button [year-to-check calendar caption change-fn]
  (try
    ; Provided this doesn't throw, we can show a clickable button
    (holi/holidays-in-year year-to-check calendar)
    [:button {:on-click #(change-fn)} caption]
    (catch ExceptionInfo _
      [:button {:disabled true} caption])))

(defn current-year [current-year set-year!]
  (r/with-let [year-input (r/atom current-year)
               label-or-field (r/atom :label)
               submit (fn [_]
                        (try
                          (set-year! @year-input)
                          (catch js/Error _
                            (reset! year-input current-year))
                          (finally
                            (reset! label-or-field :label))))]
    (condp = @label-or-field
      :label
      [:span {:on-click #(do (reset! label-or-field :field) (reset! year-input current-year))} (str current-year)]

      :field
      [:input {:type        :text
               :class       year-field-style
               :auto-focus  true
               :value       @year-input
               :on-key-down (fn [e] (when (= KEYCODE-ENTER (.-keyCode e)) (submit)))
               :on-change   (fn [e] (reset! year-input (-> e .-target .-value)))
               :on-blur     submit}])))

(defn main-calendar-view [year set-year! change-to-next! change-to-previous! calendar description]
  [:div
   [:h1 (gstr/format "%s Holiday Calendar (%s)" calendar description)]
   [inline-container {:spacing "1em" :justify :center}
    [change-button (dec year) calendar "<" change-to-previous!]
    [current-year year set-year!]
    [change-button (inc year) calendar ">" change-to-next!]]
   (into [:<>]
         (mapv holiday (holi/holidays-in-year year calendar)))])

(defn sidebar-and-main [calendar-info year]
  (let [[calendar description] @calendar-info
        change-calendar (fn [calendar description] (reset! calendar-info [calendar description]))
        selected-year @year
        set-year! (fn [y]
                    (holi/holidays-in-year y calendar) ; Throws if no holidays for that year
                    (reset! year (-> (t/year y) int)))
        change-to-next! #(swap! year inc)
        change-to-previous! #(swap! year dec)]
    [:div {:class sidebar-and-main-style}
     [sidebar change-calendar]
     [main-calendar-view selected-year set-year! change-to-next! change-to-previous! calendar description]]))

(defn header []
  [:div {:class header-style}
   [inline-container {:spacing "0em" :justify "space-between"}
    [:div "Holi calendar showcase" [version "0.17.0"]]
    [:a {:href "https://cljdoc.org/d/io.github.luciolucio/holi/CURRENT"} "Back to docs"]]])

(defn view []
  [:div {:class container-style}
   [stack-container {:spacing "0em"}
    [header]
    [horizontal-line]
    [sidebar-and-main (r/atom ["US" "United States"]) (r/atom (-> (t/year) int))]]])
