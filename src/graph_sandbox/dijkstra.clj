(ns graph-sandbox.dijkstra
  (:import [java.util PriorityQueue]))

(def graph {:A [{:B 2} {:C 4}]
            :B [{:D 2} {:F 3}]
            :C [{:E 2} {:F 3}]
            :D [{:H 2}]
            :E [{:F 3}]
            :H [{:I 2}]
            :I [{:J 1}]
            :F [{:J 1} {:G 4}]
            :G [{:J 1}]})

(defn visited? [node visited]
  (contains? (set visited) node))

(defn neighbours [node graph]
  (graph node))

(defn dijkstra-search [graph start end]
  (let [frontier (PriorityQueue.)
        visited #{start}
        came-from {start nil}]
    (loop [current (.poll frontier)
           cost-so-far {}
           next (neighbours current graph)]
      )))
