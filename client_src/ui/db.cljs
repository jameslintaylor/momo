(ns ui.db
  (:require [cljs.spec.alpha :as s]
            [re-frame.core :refer [reg-cofx]]))

(def fs (js/require "fs"))
(def os (js/require "os"))
(def path (js/require "path"))
(def mkdirp (js/require "mkdirp"))

(s/def ::id int?)
(s/def ::fs-path string?)
(s/def ::should-watch boolean?)
(s/def ::media-directory (s/keys :req [::id ::fs-path ::should-watch]))
(s/def ::media-directories (s/and
                            (s/map-of ::id ::media-directory)
                            #(instance? PersistentTreeMap %)))
(s/def ::db (s/keys :req [::media-directories]))

(def db-skeleton
  {::media-directories (sorted-map)})

(def dirs-file (str (.homedir os) "/.momo.d/dirs.edn"))

;; HELPERS

(defn read-media-dirs
  "reads the contents of the ~/.momo.d/dirs.edn text file. returns an
  empty map if no such file exists."
  []
  (try
    (-> (.readFileSync fs dirs-file)
        str
        cljs.reader/read-string)
    (catch js/Error _
      (sorted-map))))

(defn write-media-dirs
  "writes media dirs to local store"
  [dirs]
  ;; ensure directory structure exists
  (mkdirp (.dirname path dirs-file)
          (fn [err]
            (when-not err
              (.open fs dirs-file "w"
                     (fn [err fd]
                       (.write fs fd (str dirs))
                       (.close fs fd)))))))

;; COFX

(reg-cofx
 :store-dirs
 (fn [cofx _]
   (assoc cofx :store-dirs (read-media-dirs))))
