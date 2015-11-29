(ns chess-game.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]))

(enable-console-print!)

(defonce app-state (atom {:text "hello world"}))

;; (defn contacts-view [data owner]
;;   (reify
;;     om/IInitState
;;     (init-state [_]
;;       {:vect [[1 1 1]
;;               [1 1 1]
;;               [2 3 4]]})
;;     om/IRenderState
;;     (render-state [_ state]
;;                   (apply dom/div nil
;;                          (for [row (:vect state)]
;;                            (dom/p nil (str row)))))))

;; (defn foo [data owner]
;;   (reify
;;     om/IRender
;;     (render [this]
;;             (dom/div nil
;;               (dom/p nil "yo")))))

(defn x-cord
  [pair]
  (get-in pair [0]))

(defn y-cord
  [pair]
  (get-in pair [1]))

(defn pawn [data owner]
  (reify
    om/IRender
    (render [this]
            (dom/div #js {:className "chess-piece"}
                     (dom/img #js {:src "pawn.png"} nil)))))

(defn chess-cell [data owner {:keys [pos]}]
  (reify
    om/IInitState
    (init-state [_]
      {:clicked false})
    om/IRender
    (render [this]
      (dom/td #js {:className "cell"
                   :id (if (= true (om/get-state owner :clicked))
                         "light-blue"
                         data)
                   :onClick #(do
                               (om/update-state! owner :clicked not)
                               (js/console.log "the x y cords is " (x-cord pos) ", " (y-cord pos)))}
              (if (:pawn (om/get-state owner))
                (om/build pawn nil)
                nil)))))


(defn chess-board [data owner]
  (reify
    om/IInitState
    (init-state [_]
      {:x-cell (range 8), :y-cell (range 8)})
    om/IRenderState
    (render-state [_ state]
      (dom/p nil "foo fooo foo")
      (apply dom/table #js {:id "chess-board"}
        (for [y (:x-cell state)]
          (apply dom/tr nil
                 (for [x (:y-cell state)]
                   (om/build chess-cell (if (even? x)
                                          (if (even? y)
                                            "brown"
                                            "light-brown")
                                          (if (even? y)
                                            "light-brown"
                                            "brown"))
                             {:state {:pawn (if (= x 1) true)}
                              :opts {:pos [x y]}}))))))))

(defn main []
  (om/root
    (fn [app owner]
      (reify
        om/IInitState
        (init-state [_]
          {:foo (range 10)})
        om/IRender
        (render [_]
                (dom/div nil
                         (dom/p nil(:text app))
                         (om/build contacts-view nil)
                         (om/build chess-board nil)
                         (dom/button #js {:onClick #(start-game)}
                                     "Start Game")
                         (om/build foo nil)))))
    app-state
    {:target (. js/document (getElementById "app"))}))
 
