(ns petrol-examples.spotify.view
  (:require [petrol.core :refer [send! send-value!]]
            [petrol-examples.spotify.messages :as m]))

(defn- get-track-image-url
  [track]
  (-> track :album :images first :url))

(defn- track-view
  [{:keys [name]
    :as track}]
  [:div.thumbnail
   (when-let [img-src (get-track-image-url track)]
     [:img.img-responsive
      {:alt name
       :src img-src}])

   [:div.caption
    (when-let [audio-src (:preview_url track)]
      [:audio {:src audio-src
               :controls :controls}])

    [:h4 name]]])

(defn- search-form
  [ui-channel term]
  [:div
   [:input {:type :text
            :placeholder "Song name..."
            :defaultValue term
            :on-change (send-value! ui-channel m/->ChangeSearchTerm)}]
   [:button.btn.btn-success
    {:on-click (send! ui-channel (m/->Search))}
    "Go"]])

(defn root
  [ui-channel {:keys [term tracks]
              :as app}]
  [:div {:style {:display :flex
                 :flex-direction :column
                 :align-items :center}}
   [:h1 "Simple Spotify Client"]
   [search-form ui-channel term]

   [:div
    (for [track tracks]
      [:div {:key (:id track)
             :style {:height "420px"
                     :float :left
                     :margin "20px"
                     :width "330px"}}
       [track-view track]])]])
