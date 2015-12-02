(ns petrol-examples.spotify.processing
  (:require [petrol.core :refer [Message EventSource]]
            [petrol-examples.spotify.messages :as m]
            [petrol-examples.spotify.rest :as rest]))

(extend-protocol Message
  m/ChangeSearchTerm
  (process-message [{:keys [term]} app]
    (assoc app :term term)))

(extend-protocol Message
  m/Search
  (process-message [_ app] app))

(extend-protocol EventSource
  m/Search
  (watch-channels [_ {:keys [term]
             :as app}]
    #{(rest/search-songs term)}))

(extend-protocol Message
  m/SearchResults
  (process-message [response app]
    (assoc app :tracks (-> response :body :tracks :items))))
