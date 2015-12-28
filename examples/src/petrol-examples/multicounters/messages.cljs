(ns petrol-examples.multicounters.messages)

(defrecord UpdateCounter [counter-id message])
(defrecord AddCounter [])
(defrecord DelCounter [counter-id])
