(ns petrol.core
  (:require [cljs.core.async :as async :refer [alts! put! pipe chan <! >!]]
            [clojure.set :as set])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defprotocol Message
  (process-message [message app]
                   "Given a message, take the current app state and
                   return the new one. In essense this is a reducing
                   function."))

(defprotocol EventSource
  (watch-channels [message app]))

(def ^:private !channels
  (atom #{}))

(defn cmap
  "Apply a function to every element on a channel."
  [f in]
  (pipe in (chan 1 (map f))))

(defn- get-event-value
  "Given a DOM event, return the value it yields. This abstracts over
  the needless inconsistencies of the DOM."
  [event]
  (let [target (.-target event)
        type (.-type target)]
    (condp contains? type
      #{"checkbox"}
      (.-checked target)

      #{"text" "email" "password" "number" "radio" "textarea" "select-one" "select-multiple"}
      (.-value target))))

(defn send!
  "Send information from the user to the message queue.
  The message must be a record which implements the Message protocol."
  [channel message]
  (fn [dom-event]
    (put! channel message)
    (.stopPropagation dom-event)))

(defn send-value!
  "Send information from the user to the message queue.

  Similar to `send!`, except the message-fn will be called with the message's value first."
  [channel message-fn]
  (fn [dom-event]
    (->> dom-event
         get-event-value
         message-fn
         (put! channel))
    (.stopPropagation dom-event)))

(defn forward
  [f from]
  (let [to (chan)]
    (go-loop []
      (>! from (f (<! to))))
    to))

(defn start-message-loop!
  [!app render-fn]

  (reset! !channels #{})

  (let [ui-channel (async/chan)]
    (swap! !channels conj ui-channel)

    (add-watch !app :render
               (fn [_ _ _ app]
                 (render-fn ui-channel app)))

    (swap! !app identity)

    (go-loop []
      (when-let [cs (seq @!channels)]
        (let [[message channel] (alts! cs)]
          (when (nil? message)
            (swap! !channels disj channel))

          (when (satisfies? Message message)
            (swap! !app #(process-message message %)))

          (when (satisfies? EventSource message)
            (swap! !channels set/union (watch-channels message @!app))))
        (recur)))))
