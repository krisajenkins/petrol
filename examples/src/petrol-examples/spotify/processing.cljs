(ns petrol-examples.spotify.processing
  (:require [petrol.core :as petrol]
            [petrol-examples.spotify.events :as e]
            [petrol-examples.spotify.rest :as rest]))

(extend-protocol petrol/Event
  e/ChangeSearchTerm
  (handle-event [{:keys [term]} app]
    (assoc app :term term))

  e/Search
  (handle-event [_ {:keys [term]
                    :as app}]
    (->> (rest/search-songs term)
         (petrol/watch-channels app)))

  e/SearchResults
  (handle-event [response app]
    (assoc app :tracks (-> response :body :tracks :items))))
