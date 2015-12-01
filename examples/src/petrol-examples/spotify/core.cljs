(ns petrol-examples.spotify.core
  (:require [petrol.core :as petrol]
            [reagent.core :as reagent]
            [petrol-examples.spotify.processing]
            [petrol-examples.spotify.view :as view]))

(defonce !app
  (reagent/atom {}))

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
