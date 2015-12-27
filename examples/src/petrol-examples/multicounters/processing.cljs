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
    (update app :counters conj counter2/initial-state))

  m/DelCounter
  (process-message [{:keys [counter-id]} app]
    (update app :counters (fn [xs]
                            (->> (concat (take counter-id xs)
                                         (drop (inc counter-id) xs))
                                 (into []))))))
