(ns petrol.core
  (:require [cljs.core.async :as async :refer [alts! put! pipe chan]]
            [clojure.set :as set])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defprotocol Message
  (process-message [message app]
                   "Given a message, take the current app state and
                   return the new one. In essense this is a reducing
                   function."))

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

(defn watch-channels
  "Add a core.async channel to the set of channels we watch for messages."
  [app & channels]
  (update app ::channels set/union (set channels)))

(defn start-message-loop!
  [!app render-fn]

  (let [ui-channel (async/chan)]
    (add-watch !app :render
               (fn [_ _ _ app]
                 (render-fn ui-channel app)))

    ;; Iniitialise app.
    (swap! !app watch-channels ui-channel)

    (go-loop []
      (when-let [channels (seq (@!app ::channels))]
        (let [[message channel] (alts! channels)]
          (swap! !app (fn [app]
                        (if (nil? message)
                          (update app ::channels disj channel)
                          (process-message message app)))))
        (recur)))

    !app))
