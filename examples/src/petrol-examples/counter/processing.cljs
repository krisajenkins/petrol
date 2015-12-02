(ns petrol-examples.counter.processing
  (:require [petrol.core :refer [Message]]
            [petrol-examples.counter.messages :as m]))

(extend-protocol Message
  m/Decrement
  (process-message [_ app]
    (update app :counter dec))

  m/Increment
  (process-message [_ app]
    (update app :counter inc)))
