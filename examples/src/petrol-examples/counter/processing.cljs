(ns petrol-examples.counter.processing
  (:require [petrol.core :as petrol]
            [petrol-examples.counter.messages :as m]))

(extend-protocol petrol/Message
  m/Decrement
  (process-message [_ app]
    (update app :counter dec))

  m/Increment
  (process-message [_ app]
    (update app :counter inc)))
