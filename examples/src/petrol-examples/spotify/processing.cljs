(ns petrol-examples.spotify.processing
  (:require [petrol.core :as petrol]
            [petrol-examples.spotify.messages :as m]
            [petrol-examples.spotify.rest :as rest]))

(extend-protocol petrol/Message
  m/ChangeSearchTerm
  (process-message [{:keys [term]} app]
    (assoc app :term term))

  m/Search
  (process-message [_ {:keys [term]
                    :as app}]
    (->> (rest/search-songs term)
         (petrol/watch-channels app)))

  m/SearchResults
  (process-message [response app]
    (assoc app :tracks (-> response :body :tracks :items))))
