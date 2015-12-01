(defproject petrol "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]

                 ;; ClojureScript
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.2.374"]
                 [reagent "0.5.1"]
                 [cljs-http "0.1.38"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-1"]]

  :cljsbuild {:builds {:spotify {:source-paths ["src"]
                                 :figwheel {:on-jsload "spotify.core/reload-hook"}
                                 :compiler {:main spotify.core
                                            :asset-path "js/compiled/out"
                                            :output-to "resources/public/js/compiled/spotify.js"
                                            :output-dir "resources/public/js/compiled/out"
                                            :optimizations :none}}}}


  :figwheel {:repl true
             :nrepl-port 7888})
