(ns petrol-examples.counter2.events
  (:require [petrol.core :as petrol]))

(defrecord ResetCounter []
  petrol/Event
  (handle-event [_ app]
    (assoc app :counter 0)))

(defrecord ModifyCounter [n]
  petrol/Event
  (handle-event [{n :n} app]
    (update app :counter #(+ % n))))
