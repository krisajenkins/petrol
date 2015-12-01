(ns petrol-examples.counter.view
  (:require [petrol.core :refer [send!]]
            [petrol-examples.counter.messages :as m]))

(defn root
  [ui-channel app]
  [:div.container
   [:div.row
    [:div.col-xs-12.col-sm-6.col-lg-4
     [:h1 "Simple Counter"]
     [:div.well (:counter app)]
     [:div.btn-group
      [:button.btn.btn-info {:on-click (send! ui-channel (m/->Decrement))}
       "Decrement"]
      [:button.btn.btn-info {:on-click (send! ui-channel (m/->Increment))}
       "Increment"]]]]])
