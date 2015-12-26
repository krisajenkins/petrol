(ns petrol-examples.multicounters.processing
  (:require [petrol.core :refer [Message process-message]]
            [petrol-examples.counter2.core :as counter2]
            [petrol-examples.multicounters.messages :as m]))

(extend-protocol Message
  m/UpdateCounter
  (process-message [{:keys [message counter-id]} app]
    (update-in app [:counters counter-id] #(process-message message %)))

  m/AddCounter
  (process-message [_ app]
    (let [new-id (inc (:last-counter-id app))]
      (-> (assoc-in app [:counters new-id] counter2/initial-state)
          (assoc :last-counter-id new-id))))

  m/DelCounter
  (process-message [{:keys [counter-id]} app]
    (update app :counters #(dissoc % counter-id))))
