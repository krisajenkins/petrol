(ns petrol-examples.counter2.core
  (:require [petrol.core :as petrol]
            [reagent.core :as reagent]
            [cljs.core.async :as async :refer [alts!]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defonce !app
  (reagent/atom {:counter 0}))

;; figwheel reload-hook
(defn reload-hook
  []
  (swap! !app identity))

(defrecord ResetCounter []
  petrol/Event
  (handle-event [_ app]
    (assoc app :counter 0)))

(defrecord ModifyCounter [n]
  petrol/Event
  (handle-event [{n :n} app]
    (update app :counter #(+ % n))))

(defn root-view
  [ui-events app]
  [:div.container
   [:h1 "Demo"]
   [:div.well (:counter app)]
   [:button.btn.btn-danger {:on-click (petrol/send! ui-events (->ResetCounter))}
    "Reset"]
   (for [[label delta] [
                        ["Decrement" -1]
                        ["Increment" 1]
                        ["+ 5" 5]
                        ["+ 10" 10]]]
     [:button.btn.btn-info {:key delta
                            :on-click (petrol/send! ui-events (->ModifyCounter delta))}
      label])])

(defn render-fn
  [ui-events app]
  (reagent/render-component [root-view ui-events app]
                            js/document.body))

(defn ^:export main
  []
  (enable-console-print!)
  (petrol/start-event-loop !app render-fn))
