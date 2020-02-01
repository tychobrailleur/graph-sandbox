(ns graph-sandbox.grid)

;; https://www.redblobgames.com/pathfinding/grids/graphs.html

(defn all-nodes [width height]
  (for [x (range width)
        y (range height)]
    [x y]))


(defn neighbours [node]
  (let [dirs [[1 0] [0 1] [-1 0] [0 -1]]
        result []]
    (loop [dir dirs
           r result]
      (let [[x y] (first dir)
            [u v] node]
        (if (empty? dir)
          r
          (recur (rest dir)
                 (conj r [(+ x u) (+ y v)])))))))
