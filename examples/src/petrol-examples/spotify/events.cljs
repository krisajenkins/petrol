(ns petrol-examples.spotify.events)

(defrecord ChangeSearchTerm [term])

(defrecord Search [])

(defrecord SearchResults [body])
