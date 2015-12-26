(ns petrol-examples.multicounters.view
  (:require [petrol.core :refer [send! forward]]
            [petrol-examples.multicounters.messages :as m]
            [petrol-examples.counter2.view :as counter2]))

(defn root
  [ui-channel app]
  [:div.container
   [:h1 "Multiple Counters: " (count (:counters app))]

   [:div
    [:button {:on-click (send! ui-channel (m/->AddCounter))} "Add counter"]]

   [:div.row
    [:div.col-xs-12.col-sm-6
     (for [[key val] (:counters app)] ^{:key key}
      [:div
       [counter2/root (forward (partial m/->UpdateCounter key)
                               ui-channel) val]
       [:button {:on-click (send! ui-channel (m/->DelCounter key))} "Delete counter"]])]]])
