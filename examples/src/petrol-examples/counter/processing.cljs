(ns petrol-examples.counter.processing
  (:require [petrol.core :refer [Message]]
            [petrol-examples.counter.messages :as m]))

(extend-protocol Message
  m/Decrement
  (process-message [_ state]
    (dec state))

  m/Increment
  (process-message [_ state]
    (inc state)))
