(ns graph-sandbox.heuristic
  (:require [clojure.data.priority-map :refer [priority-map]]
            [graph-sandbox.common :refer [neighbours]]))

(def graph {:A [{:B 2} {:C 4}]
            :B [{:D 2} {:F 3}]
            :C [{:E 2} {:F 3}]
            :D [{:H 2}]
            :E [{:F 3}]
            :H [{:I 2}]
            :I [{:J 1}]
            :F [{:J 1} {:G 4}]
            :G [{:J 1}]})

;; We use an arbitrary matrix evaluating distance to goal.
;;       A B C D E F G H I J
(def h [[0 1 2 2 3 2 4 3 4 3] ; A
        [1 0 3 1 3 1 3 2 3 2] ; B
        [2 3 0 4 1 1 3 5 4 2] ; C
        [2 1 4 0 5 2 2 1 2 2] ; D
        [3 3 1 5 0 1 2 6 3 2] ; E
        [2 1 1 2 1 0 2 2 2 1] ; F
        [4 3 3 2 2 2 0 2 1 1] ; G
        [3 2 5 1 6 2 2 0 1 2] ; H
        [4 3 4 2 3 2 1 1 0 1] ; I
        [3 2 2 2 2 1 1 2 1 0] ; J
        ])

;; Convenience map to convert node to matrix index.
(def letter->digit {:A 0
                    :B 1
                    :C 2
                    :D 3
                    :E 4
                    :F 5
                    :G 6
                    :H 7
                    :I 8
                    :J 9})

(defn heuristic
  "Estimated distance to goal from current node."
  [goal current]
  (-> h
      (nth (letter->digit current))
      (nth (letter->digit goal))))

(defn visit-neighbours [u goal neighbours frontier came-from]
  (loop [n neighbours
         f frontier
         c came-from]
    (if (empty? n)
      {:frontier f :from c}
      (let [[v _] (first (first n))]
        (if-not (contains? c v)
          (recur (rest n) (assoc f v (heuristic goal v)) (assoc c v u))
          (recur (rest n) f c))))))

(defn heuristic-search [graph start end]
  (loop [frontier (priority-map start 0)
         came-from {start nil}]
    (let [current (first (peek frontier))
          neighbours (neighbours graph current)
          next (visit-neighbours current end neighbours (pop frontier) came-from)]
      (if (= current end)
        {:from came-from}
        (recur (next :frontier) (next :from))))))
