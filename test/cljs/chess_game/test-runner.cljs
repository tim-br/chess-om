(ns chess_game.test-runner
  (:require
   [cljs.test :refer-macros [run-tests]]
   [chess_game.core-test]))

(enable-console-print!)

(defn runner []
  (if (cljs.test/successful?
       (run-tests
        'chess_game.core-test))
    0
    1))
