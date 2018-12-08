(ns clj-sketches.core
  (:require [reagent.core :as r :refer [atom]]
            [clj-sketches.place :as place]))

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:sketch-name "Oh, hello!"
                          :prev ""
                          :next ""
                          :sketch-index 0}))

(enable-console-print!)

;; Siphon this out into its own little functional library!
(defn screen []
   (r/with-let [s (place/screen2D)]
     (.-domElement s)))

(defn mouse-pos-comp []
  (r/with-let [pointer (r/atom nil)
               handler #(swap! pointer assoc
                               :x (.-pageX %)
                               :y (.-pageY %))
               _ (.addEventListener js/document "mousemove" handler)]
    [:div
     "Pointer moved to: "
     (str @pointer)]
    (finally
      (.removeEventListener js/document "mousemove" handler))))


(defn page-one []
  [:div
   [:h1 (:sketch-name @app-state)]
   [:h3 "Edit this and watch it change!"]
   ;; [:div#screen2D]
   (mouse-pos-comp)
   (screen)
   ])

;; Identical ... for now!
(defn page-two []
  [:div
   [:h1 (:sketch-name @app-state)]
   [:h3 "Edit this and watch it change!"]
   ;; [:div#screen2D]
   (screen)
   ])

(def pages [
            page-one
            page-two
            ])

(println "This text is printed from src/clj-sketches/core.cljs. Go ahead and edit it and see reloading in action.")

(defn sketch []
  (pages (:sketch-index @app-state)))


(r/render-component [sketch]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
