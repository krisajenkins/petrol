(ns petrol-examples.hydra.core
  (:require [petrol.core :as petrol]
            [reagent.core :as reagent]
            [petrol-examples.counter.core :as counter]
            [petrol-examples.counter2.core :as counter2]
            [petrol-examples.spotify.core :as spotify]
            [petrol-examples.hydra.processing]
            [petrol-examples.hydra.view :as view]))

(defonce !app
  (reagent/atom {:counter    counter/initial-state
                 :counter2a counter2/initial-state
                 :counter2b counter2/initial-state
                 :spotify    spotify/initial-state}))

;; figwheel reload-hook
(defn reload-hook
  []
  (swap! !app identity))

(defn render-fn
  [ui-channel app]
  (reagent/render-component [view/root ui-channel app]
                            js/document.body))

(defn ^:export main
  []
  (enable-console-print!)
  (petrol/start-message-loop! !app render-fn))
