(ns clj-sketches.place
  (:require cljsjs.three))

(defn screen2D []
  ;; FIXME: give back a three.js scene with an init & an update
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
    ;Kick off the animation loop updating
    (defn render []
      ;; (aset mesh "rotation" "y" (+ 0.01 (.-y (.-rotation mesh))))
      (aset mesh "rotation" "y" (+ 0.01 (.-y (.-rotation mesh))))
      (.render renderer scene p-camera))

    (defn animate []
      (.requestAnimationFrame js/window animate)
      (render))

    (animate)
    ;; renderer
    (.appendChild js/document.body (.-domElement renderer))
    (.-domElement renderer)))
