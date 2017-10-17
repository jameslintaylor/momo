(ns ui.ipc
  (:require [re-frame.core :refer [reg-fx]]))

(def ipcRenderer (.-ipcRenderer (js/require "electron")))

(.on ipcRenderer "show-file-explorer"
     (fn [event, arg]
       (.log js/console arg)))

(reg-fx
 :show-file-explorer
 (fn []
   (print "why hello fx")
   (.send ipcRenderer "show-file-explorer" [])))


