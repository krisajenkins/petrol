(ns petrol-examples.spotify.rest
  (:require [cljs-http.client :as http]
            [petrol.core :as petrol]
            [petrol-examples.spotify.messages :as m]))

(defn search-songs
  [term]
  (petrol/cmap m/map->SearchResults
               (http/get "https://api.spotify.com/v1/search"
                         {:with-credentials? false
                          :query-params {:q term
                                         :type "track"}})))
