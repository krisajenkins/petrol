(ns petrol-examples.hydra.processing
  (:require [petrol.core :refer [Message process-message EventSource watch-channels
                                 process-submessage watch-subchannels]]
            [petrol-examples.spotify.processing]
            [petrol-examples.hydra.messages :as m]))

(extend-protocol Message
  m/Counter
  (process-message [{:keys [submessage]} app]
    (process-submessage submessage app [:counter]))

  m/Counter2A
  (process-message [{:keys [submessage]} app]
    (process-submessage submessage app [:counter2a]))

  m/Counter2B
  (process-message [{:keys [submessage]} app]
    (process-submessage submessage app [:counter2b]))

  m/Spotify
  (process-message [{:keys [submessage]} app]
    (process-submessage submessage app [:spotify])))

(extend-protocol EventSource
  m/Spotify
  (watch-channels [{:keys [submessage]} app]
    (watch-subchannels submessage app [:spotify] m/->Spotify)))
