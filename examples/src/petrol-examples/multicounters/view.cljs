(ns petrol-examples.multicounters.view
  (:require [petrol.core :refer [send! forward]]
            [petrol-examples.multicounters.messages :as m]
            [petrol-examples.counter2.view :as counter2]))

(defn counter-container
  [ui-channel index counter]
  [:div.col-xs-12.col-sm-4 {:key index}
   [counter2/root (forward (partial m/->UpdateCounter index)
                           ui-channel)
    counter]
   [:button.btn.btn-info {:on-click (send! ui-channel (m/->DelCounter index))}
    "Delete counter"]])

(defn root
  [ui-channel app]
  [:div.container
   [:h1 "Multiple Counters: " (count (:counters app))]

   [:div
    [:button.btn.btn-info {:on-click (send! ui-channel (m/->AddCounter))}
     "Add counter"]]

   [:div.row
    (map-indexed (partial counter-container ui-channel)
                 (:counters app))]])
