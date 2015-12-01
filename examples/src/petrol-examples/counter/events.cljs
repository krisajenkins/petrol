(ns petrol-examples.counter.events
  (:require [petrol.core :refer [Event]]))

(defrecord Decrement []
  Event
  (handle-event [_ app]
    (update app :counter dec)))

(defrecord Increment []
  Event
  (handle-event [_ app]
    (update app :counter inc)))
