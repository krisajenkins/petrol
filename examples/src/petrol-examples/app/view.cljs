(ns petrol-examples.app.view
  (:require [petrol.core :refer [send! send-value! forward]]
            [petrol-examples.app.messages :as m]))

(defn root
  [ui-channel app]
  [:div.container
   [:h1 "Demo App"]
   [:div [:code (pr-str app)]]
   [:ul.list-group
    [:li.list-group-item [:a {:href "#bar"}
                          "Foo"]]]])
