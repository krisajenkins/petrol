(ns petrol.core
  (:require [cljs.core.async :as async :refer [alts! put! pipe chan]]
            [clojure.set :as set])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defprotocol Event
  (handle-event [event app]
                "Given an event, take the current app state and return the new one. In essense this is a reducing function."))

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
  [channel message]
  (fn [dom-event]
    (put! channel message)
    (.stopPropagation dom-event)))

(defn send-value!
  [channel message-fn]
  (fn [dom-event]
    (->> dom-event
         get-event-value
         message-fn
         (put! channel))
    (.stopPropagation dom-event)))

(defn watch-channels
  [app & channels]
  (update app ::channels set/union (set channels)))

(defn start-event-loop!
  [!app render-fn]

  (let [ui-events (async/chan)]
    (add-watch !app :render
               (fn [_ _ _ app]
                 (render-fn ui-events app)))

    ;; Iniitialise app.
    (swap! !app watch-channels ui-events)

    (go-loop []
      (when-let [channels (seq (@!app ::channels))]
        (let [[event channel] (alts! channels)]
          (swap! !app (fn [app]
                        (if (nil? event)
                          (update app ::channels disj channel)
                          (handle-event event app)))))
        (recur)))

    !app))
