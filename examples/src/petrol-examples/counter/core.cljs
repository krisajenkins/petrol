(ns petrol-examples.counter.core
  (:require [petrol.core :as petrol]
            [reagent.core :as reagent]
            [petrol-examples.counter.processing]
            [petrol-examples.counter.view :as view]))

(def initial-state
  {:counter 0})

(defonce !app
  (reagent/atom initial-state))

;; figwheel reload-hook
(defn reload-hook
  []
  (swap! !app identity))

(defn handle-with! [ui-channel fmap]
  (fn [message]
    (let [focused-message
          (reify petrol/Message
            (process-message [this state]
              (fmap #(petrol/process-message message %) state)))]
      (petrol/send! ui-channel focused-message))))

(defn render-fn
  [ui-channel app]
  (reagent/render-component
    [view/root (handle-with! ui-channel #(update %2 :counter %1)) (:counter app)]
    js/document.body))

(defn ^:export main
  []
  (enable-console-print!)
  (petrol/start-message-loop! !app render-fn))
