(ns petrol-examples.counter.view
  (:require [petrol.core :as petrol]
            [petrol-examples.counter.events :as e]))

(defn root
  [ui-events app]
  [:div.container
   [:h1 "Demo"]
   [:div.well (:counter app)]
   [:button.btn.btn-info {:on-click (petrol/send! ui-events (e/->Decrement))}
    "Decrement"]
   [:button.btn.btn-info {:on-click (petrol/send! ui-events (e/->Increment))}
    "Increment"]])
