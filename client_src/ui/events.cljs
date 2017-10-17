(ns ui.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx inject-cofx path after]]
            [ui.db :refer [write-media-dirs]]
            [cljs.spec.alpha :as s]))

(defn check-and-throw
  "Throws an exception if `value` doesn't match the Spec `a-spec`."
  [a-spec value]
  (when-not (s/valid? a-spec value)
    (throw (ex-info (str "spec check failed: " (s/explain-str a-spec value)) {}))))

(def check-spec-interceptor (after (partial check-and-throw :ui.db/db)))

(def write-media-dirs-interceptor (after write-media-dirs))

;; EVENT HANDLERS

(reg-event-fx
 :initialize-db

 [(inject-cofx :store-dirs)
  check-spec-interceptor]

 (fn [{:keys [db store-dirs] :as cofx} _]
   (print "hello")
   (print cofx)
   {:db {::media-directories store-dirs}}))

(reg-event-fx
 :add-dir-button-press
 (fn [_ _]
   (print "oh joy a new directory!")
   {:show-file-explorer []}))
