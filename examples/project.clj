(defproject petrol-examples "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]

                 ;; ClojureScript
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.2.374"]
                 [petrol "0.1.3"]
                 [reagent "0.5.1"]
                 [bidi "1.24.0"]
                 [com.cemerick/url "0.1.1"]
                 [kibu/pushy "0.3.6"]
                 [cljs-http "0.1.38"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-1"]]

  :cljsbuild {:builds {:counter {:source-paths ["src"]
                                 :figwheel {:on-jsload "petrol-examples.counter.core/reload-hook"}
                                 :compiler {:main petrol-examples.counter.core
                                            :asset-path "js/counter/compiled/out"
                                            :output-to "resources/public/js/counter/compiled/counter.js"
                                            :output-dir "resources/public/js/counter/compiled/out"
                                            :optimizations :none}}
                       :counter2 {:source-paths ["src"]
                                  :figwheel {:on-jsload "petrol-examples.counter2.core/reload-hook"}
                                  :compiler {:main petrol-examples.counter2.core
                                             :asset-path "js/counter2/compiled/out"
                                             :output-to "resources/public/js/counter2/compiled/counter2.js"
                                             :output-dir "resources/public/js/counter2/compiled/out"
                                             :optimizations :none}}
                       :spotify {:source-paths ["src"]
                                 :figwheel {:on-jsload "petrol-examples.spotify.core/reload-hook"}
                                 :compiler {:main petrol-examples.spotify.core
                                            :asset-path "js/spotify/compiled/out"
                                            :output-to "resources/public/js/spotify/compiled/spotify.js"
                                            :output-dir "resources/public/js/spotify/compiled/out"
                                            :optimizations :none}}
                       :hydra {:source-paths ["src"]
                               :figwheel {:on-jsload "petrol-examples.hydra.core/reload-hook"}
                               :compiler {:main petrol-examples.hydra.core
                                          :asset-path "js/hydra/compiled/out"
                                          :output-to "resources/public/js/hydra/compiled/hydra.js"
                                          :output-dir "resources/public/js/hydra/compiled/out"
                                          :optimizations :none}}
                       :pages {:source-paths ["src"]
                               :figwheel {:on-jsload "petrol-examples.pages.core/reload-hook"}
                               :compiler {:main petrol-examples.pages.core
                                          :asset-path "js/pages/compiled/out"
                                          :output-to "resources/public/js/pages/compiled/pages.js"
                                          :output-dir "resources/public/js/pages/compiled/out"
                                          :optimizations :none}}
                       :multicounters {:source-paths ["src"]
                                       :figwheel {:on-jsload "petrol-examples.multicounters.core/reload-hook"}
                                       :compiler {:main petrol-examples.multicounters.core
                                                  :asset-path "js/multicounters/compiled/out"
                                                  :output-to "resources/public/js/multicounters/compiled/multicounters.js"
                                                  :output-dir "resources/public/js/multicounters/compiled/out"
                                                  :optimizations :none}}}}

  :figwheel {:repl true
             :nrepl-port 7888})
