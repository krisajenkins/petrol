(ns petrol-examples.counter2.messages)

(defrecord ResetCounter [])

(defrecord ModifyCounter [n])

(defrecord KeyEvent [c])
