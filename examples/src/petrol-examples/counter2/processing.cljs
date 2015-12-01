(ns petrol-examples.counter2.processing
  (:require [petrol.core :as petrol]
            [petrol-examples.counter2.messages :as m]))

(extend-protocol petrol/Message
  m/ResetCounter
  (process-message [_ app]
    (assoc app :counter 0))

  m/ModifyCounter
  (process-message [{n :n} app]
    (update app :counter #(+ % n))))
