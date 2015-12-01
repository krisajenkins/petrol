(ns petrol-examples.hydra.processing
  (:require [petrol.core :refer [Message process-message EventSource watch-channels cmap]]
            [petrol-examples.spotify.processing]
            [petrol-examples.hydra.messages :as m]))

(extend-protocol Message
  m/Counter
  (process-message [{:keys [message]} app]
    (update app :counter   #(process-message message %)))

  m/Counter2A
  (process-message [{:keys [message]} app]
    (update app :counter2a #(process-message message %)))

  m/Counter2B
  (process-message [{:keys [message]} app]
    (update app :counter2b #(process-message message %)))

  m/Spotify
  (process-message [{:keys [message]} app]
    (update app :spotify   #(process-message message %))))

(extend-protocol EventSource
  m/Spotify
  (watch-channels [{:keys [message]} app]
    (when (satisfies? EventSource message)
      (map #(cmap m/->Spotify %)
           (watch-channels message (:spotify app))))))
