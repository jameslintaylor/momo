(defproject momo "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/clojurescript "1.9.908"]
                 [org.clojure/core.async  "0.3.443"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.2"]]

  :plugins [[lein-figwheel "0.5.13"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["host_src" "client_src"]

  :cljsbuild {:builds
              
              [{:id "client-dev"
                :source-paths ["client_src"]
                :figwheel true
                :compiler {:main ui.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/ui.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads [devtools.preload]}}

               {:id "host-dev"
                :source-paths ["host_src"]
                :figwheel true
                :compiler {:main main.core
                           :asset-path "target/js/compiled/out"
                           :output-to "target/js/compiled/main.js"
                           :output-dir "target/js/compiled/out"
                           :target :nodejs}}]}

  :figwheel {:css-dirs ["resources/public/css"]}
  
  :profiles {:dev {:dependencies [[binaryage/devtools "0.9.4"]
                                  [figwheel-sidecar "0.5.13"]
                                  [com.cemerick/piggieback "0.2.2"]]

                   :source-paths ["host_src" "client_src" "dev"]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     "target/js/compiled"
                                                     :target-path]}})
