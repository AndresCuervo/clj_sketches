(ns clj-sketches.core
    (:require [reagent.core :as reagent :refer [atom]]
              cljsjs.three))

(enable-console-print!)

(defn init-three []
  ;;First initiate the basic elements of a THREE scene
  (let [scene    (js/THREE.Scene.)
        p-camera (js/THREE.PerspectiveCamera.
                    ;;view-angle aspect near far
75 (/ js/window.innerWidth js/window.innerHeight) 0.1 1000
                    )
        box      (js/THREE.BoxGeometry.
                    200 200 200)
        mat      (js/THREE.MeshBasicMaterial.
                    (js-obj "color" 0xff0000
                            "wireframe" true))
        mesh     (js/THREE.Mesh. box mat)
        renderer (js/THREE.WebGLRenderer.)]

    ;;Change the starting position of cube and camera
    (aset p-camera "name" "p-camera")
    (aset p-camera "position" "z" 500)
    (aset mesh "rotation" "x" 45)
    (aset mesh "rotation" "y" 0)
    (.setSize renderer 500 500)

    ;;Add camera, mesh and box to scene and then that to DOM node.
    (.add scene p-camera)
    (.add scene mesh)
    (.appendChild js/document.body (.-domElement renderer))

    (js/document.body.querySelector "#screen2D")

    ;Kick off the animation loop updating
    (defn render []
      (aset mesh "rotation" "y" (+ 0.01 (.-y (.-rotation mesh))))
      (.render renderer scene p-camera))

    (defn animate []
      (.requestAnimationFrame js/window animate)
      (render))

    (animate)))

(println "This text is printed from src/clj-sketches/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (atom {:sketch-name ""
                          :prev ""
                          :next ""}))

(defn sketch []
  [:div
   [:h1 (:sketch-name @app-state)]
   [:h3 "Edit this and watch it change!"]
   [:div#screen2D]
   (init-three)])


(reagent/render-component [sketch]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
