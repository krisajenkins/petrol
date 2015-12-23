(ns petrol.routing
  (:require [cljs.core.async :as async :refer [chan put!]]
            [pushy.core :as pushy]
            [bidi.bidi :as bidi]
            [cemerick.url :as url]))

(defrecord UrlHistoryEvent [view])

(defn init [bidi-routes]
  (let [url-history-events (chan 1)
        uri-history (pushy/pushy #(put! url-history-events (->UrlHistoryEvent %))
                                 (fn [route]
                                   (let [url (url/url route)]
                                     (bidi/match-route bidi-routes (:path url)))))]

    (pushy/start! uri-history)
    url-history-events))
