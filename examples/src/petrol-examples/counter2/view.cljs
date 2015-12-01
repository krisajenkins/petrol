(ns petrol-examples.counter2.view
  (:require [petrol.core :as petrol]
            [petrol-examples.counter2.events :as e]))

(defn root
  [ui-events app]
  [:div.container
   [:div.row
    [:div.col-xs-12.col-sm-6.col-md-4
     [:h1 "Demo"]
     [:div.well (:counter app)]
     [:div.btn-group
      [:button.btn.btn-danger {:on-click (petrol/send! ui-events (e/->ResetCounter))}
       "Reset"]
      (for [[label delta] [
                           ["Decrement" -1]
                           ["Increment" 1]
                           ["+ 5" 5]
                           ["+ 10" 10]]]
        [:button.btn.btn-info {:key delta
                               :on-click (petrol/send! ui-events (e/->ModifyCounter delta))}
         label])]]]])
