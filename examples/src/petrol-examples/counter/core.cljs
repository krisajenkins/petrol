(ns petrol-examples.counter.core
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

(defrecord Decrement []
  petrol/Event
  (handle-event [_ app]
    (update app :counter dec)))

(defrecord Increment []
  petrol/Event
  (handle-event [_ app]
    (update app :counter inc)))

(defn root-view
  [ui-events app]
  [:div.container
   [:h1 "Demo"]
   [:div.well (:counter app)]
   [:button.btn.btn-info {:on-click (petrol/send! ui-events (->Decrement))}
    "Decrement"]
   [:button.btn.btn-info {:on-click (petrol/send! ui-events (->Increment))}
    "Increment"]])

(defn render-fn
  [ui-events app]
  (reagent/render-component [root-view ui-events app]
                            js/document.body))

(defn ^:export main
  []
  (enable-console-print!)
  (petrol/start-event-loop !app render-fn))
