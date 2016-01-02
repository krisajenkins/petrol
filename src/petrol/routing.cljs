(ns petrol.routing
  (:require [cljs.core.async :as async :refer [chan put!]]
            [pushy.core :as pushy]
            [bidi.bidi :as bidi]
            [cemerick.url :as url]))

(defrecord UrlHistoryEvent [view])

(defn make-hash-change-handler
  [url-history-events])

(defn init [bidi-routes]
  (let [url-history-events (chan 1)
        hash-change-handler (fn [event]
                              (let [hash (aget event "target" "location" "hash")
                                    view (bidi/match-route bidi-routes hash)]
                                (put! url-history-events (->UrlHistoryEvent view))))]

    ;; Register listener.
    (.addEventListener js/window
                       "hashchange"
                       hash-change-handler
                       false)

    ;; Seed the initial value.
    (hash-change-handler (clj->js {:target js/window}))

    url-history-events))

(defn href-for
  ([bidi-routes handler]
   (href-for bidi-routes handler {}))
  ([bidi-routes handler args]
   (apply bidi/path-for bidi-routes handler (mapcat identity args))))
