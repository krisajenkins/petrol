(ns petrol-examples.hydra.view
  (:require [petrol.core :refer [send! send-value! forward]]
            [petrol-examples.hydra.messages :as m]
            [petrol-examples.counter.view :as counter]
            [petrol-examples.counter2.view :as counter2]
            [petrol-examples.spotify.view :as spotify]))

(defn root
  [ui-channel app]
  [:div.container
   [:h1 "Everything"]

   [:div.row
    [:div.col-xs-12.col-sm-6
     [counter/root  (forward m/->Counter   ui-channel) (:counter app)]
     [counter2/root (forward m/->Counter2A ui-channel) (:counter2a app)]
     [counter2/root (forward m/->Counter2B ui-channel) (:counter2b app)]]

    [:div.col-xs-12.col-sm-6
     [spotify/root  (forward m/->Spotify   ui-channel) (:spotify app)]]]])
