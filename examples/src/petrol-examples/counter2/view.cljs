(ns petrol-examples.counter2.view
  (:require [petrol.core :refer [send!]]
            [petrol-examples.counter2.messages :as m]))

(defn root
  [ui-channel app]
  [:div.container
   [:div.row
    [:div.col-xs-12.col-sm-6.col-lg-4
     [:h1 "Interesting Counter!"]
     [:div.well (:counter app)]
     [:div.btn-group
      [:button.btn.btn-danger {:on-click (send! ui-channel (m/->ResetCounter))}
       "Reset"]
      (for [[label delta] [["Decrement" -1]
                           ["Increment" 1]
                           ["+ 5" 5]
                           ["+ 10" 10]]]
        [:button.btn.btn-info {:key delta
                               :on-click (send! ui-channel (m/->ModifyCounter delta))}
         label])]]]])
