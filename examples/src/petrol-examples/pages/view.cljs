(ns petrol-examples.pages.view
  (:require [petrol-examples.pages.routes :refer [href-for]]))

(defn root
  [ui-channel app]
  [:div.container
   [:h1 "Routing Demo"]

   [:div.row
    [:div.col-xs-12.col-sm-4
     [:ul.list-group (for [[link title] [[(href-for :frontpage) "Home"]
                                         [(href-for :foo) "Foo"]
                                         [(href-for :bar) "Bar"]]]
                       (with-meta
                         [:li.list-group-item [:a {:href link} title]]
                         {:key title}))]]

    [:div.col-xs-12.col-sm-8
     [:div.well
      (case (-> app :view :handler)
        :foo "This is the Foo page."
        :bar "This is the Bar page."
        "Home")]]]


   [:h2 "Debugging app state:"]
   [:div [:code (pr-str app)]]])
