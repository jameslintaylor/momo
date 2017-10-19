(ns main.ipc)

(def electron (js/require "electron"))
(def ipcMain (.-ipcMain electron))
(def dialog (.-dialog electron))

(.on ipcMain "show-file-explorer"
     (fn [event arg]
       (print "wow!")
       (.showOpenDialog
        dialog
        (clj->js {:properties ["openDirectory"]})
        (fn [dirs]
          (print (js->clj dirs))))
       
       #_(-> Eventpc
             .-sender
             (.send :show-file-explorer "I got your message boo"))))
