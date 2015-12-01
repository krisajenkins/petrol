(ns petrol.core
  (:require [cljs.core.async :as async :refer [alts! put!]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]))

(defprotocol Event
  (handle-event [event app]
                "Given an event, take the current app state and return the new one. In essense this is a reducing function."))

(defn send!
  [channel message]
  (fn [dom-event]
    (put! channel message)
    (.stopPropagation dom-event)))

(defn start-event-loop!
  [!app render-fn]

  (let [ui-events (async/chan)]
    (add-watch !app :render
               (fn [_ _ _ app]
                 (render-fn ui-events app)))

    ;; Iniitialise app.
    (swap! !app assoc ::channels #{ui-events})

    (go-loop []
      (when-let [channels (seq (@!app ::channels))]
        (let [[event channel] (alts! channels)]
          (swap! !app (fn [app]
                        (if (nil? event)
                          (update app ::channels disj channel)
                          (handle-event event app)))))
        (recur)))

    !app))
