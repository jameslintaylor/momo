(ns main.core
  (:require [server.core :as server]
            ;; just to compile - maybe I should put all ipc
            ;; registration in a method?
            [main.ipc]))

(def electron (js/require "electron"))

(def app (.-app electron))
(def browser-window (.-BrowserWindow electron))
(def crash-reporter (.-crashReporter electron))

(def main-window (atom nil))

(enable-console-print!)

(defn init-browser []
  (reset! main-window (browser-window.
                       (clj->js {:width 800
                                 :height 600
                                 :backgroundColor "#000"
                                 :titleBarStyle "hidden"})))
  ;; Path is relative to the compiled js file (main.js in our case)
  (.loadURL ^js/electron.BrowserWindow @main-window (str "file://" js/__dirname "/../../../../../resources/public/index.html"))
  (.on ^js/electron.BrowserWindow @main-window "closed" #(reset! main-window nil)))

(.on app "window-all-closed"
     (fn []
       (when-not (= js/process.platform "darwin")
         (.quit app))))

(.on app "ready"
     (fn []
       (init-browser)
       (server/start 4040)))
