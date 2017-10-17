(ns ui.core
  (:require [reagent.core :as reagent :refer [atom]]
            [devtools.core :as devtools]
            [re-frame.core :as rf]
            ;; just needed so these get compiled
            [ui.events]
            [ui.ipc]))

(devtools/install!)
(enable-console-print!)

(def electron (js/require "electron"))
(def dialog (.-dialog electron))

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "ok!"}))

(defn add-dir-button
  []
  [:div#add-dir-button {:on-click #(rf/dispatch [:add-dir-button-press])}])

(defn root []
  [add-dir-button])

(reagent/render-component [root]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in (:__figwheel_counter) inc)
)
