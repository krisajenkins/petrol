(ns petrol-examples.counter.view
  (:require [petrol-examples.counter.messages :as m]))

(defn root
  [handle! state]
  [:div.container
   [:div.row
    [:div.col-xs-12.col-sm-6.col-lg-4
     [:h1 "Simple Counter"]
     [:div.well state]
     [:div.btn-group
      [:button.btn.btn-info {:on-click (handle! (m/->Decrement))}
       "Decrement"]
      [:button.btn.btn-info {:on-click (handle! (m/->Increment))}
       "Increment"]]]]])
