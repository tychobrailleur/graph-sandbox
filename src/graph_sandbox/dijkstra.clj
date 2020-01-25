(ns graph-sandbox.dijkstra
  (:require [clojure.data.priority-map :refer [priority-map]]))

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

(defn cost [graph from to]
  (->> (graph from)
       (some #(when (contains? % to) %))
       to))

(defn next-cost [cost-so-far current next]
  (let [c (cost-so-far current)]
    (sort-by (comp second first)
             (map #(reduce-kv (fn [map key val] (assoc map key (+ c val))) {} %) next))))

(defn merge-min-map [m other]
  (merge-with #(if (< %1 %2) %1 %2) m other))

(defn merge-min-cost-map [came-from from cost new-cost]
  (if (empty? new-cost)
    came-from
    (let [[k v] (first new-cost)]
      (if (or (not (contains? cost k))
              (< v (cost k)))
        (merge-min-cost-map (assoc came-from k from) from cost (dissoc new-cost k))
        (merge-min-cost-map came-from from cost (dissoc new-cost k))))))

(defn dijkstra-search [graph start end]
  (loop [frontier (priority-map start 0)
         came-from {start nil}
         cost-so-far {start 0}]
    (let [current (first (peek frontier))
          next (neighbours current graph)
          costs (reduce merge {} (next-cost cost-so-far current next))]
      (println came-from cost-so-far)
      (if (= current end)
        {:from came-from :cost cost-so-far}
        (recur (merge-with #(if (< %1 %2) %1 %2) (pop frontier) costs)
               (merge-min-cost-map came-from current cost-so-far costs)
               ;; (reduce #(assoc %1 %2 current) came-from (flatten (map keys next)))
               (merge-with #(if (< %1 %2) %1 %2) frontier costs))))))

(defn path [graph start end f]
  (let [res (:from (f graph start end))]
    (loop [p (res end)
           t [end]]
      (if (= p start)
        (reverse (conj t start))
        (recur (res p)
               (conj t p))))))
