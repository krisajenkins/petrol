(ns petrol-examples.counter2.processing
  (:require [petrol.core :refer [Message]]
            [petrol-examples.counter2.messages :as m]))

(defn reset-counter [app]
  (assoc app :counter 0))

(extend-protocol Message
  m/ResetCounter
  (process-message [_ app]
    (reset-counter app))

  m/ModifyCounter
  (process-message [{n :n} app]
    (update app :counter #(+ % n)))

  m/KeyEvent
  (process-message [{:keys [c]} app]
    (case c
      27 (reset-counter app)
      app)))
