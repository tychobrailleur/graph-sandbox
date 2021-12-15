(ns graph-sandbox.dijkstra
  (:require [clojure.data.priority-map :refer [priority-map]]
            [graph-sandbox.common :as common :refer [neighbours infinity visited?]]))

(def graph {:A [{:B 2} {:C 4}]
            :B [{:D 2} {:F 3}]
            :C [{:E 2} {:F 3}]
            :D [{:H 2}]
            :E [{:F 3}]
            :H [{:I 2}]
            :I [{:J 1}]
            :F [{:J 1} {:G 4}]
            :G [{:J 1}]})

;; (defn next-cost [cost-so-far current next]
;;   (let [c (cost-so-far current)]
;;     (sort-by (comp second first)
;;              (map #(reduce-kv (fn [map key val] (assoc map key (+ c val))) {} %) next))))

;; (defn dijkstra-search [graph start end]
;;   (loop [frontier (priority-map start 0)
;;          came-from {start nil}
;;          cost-so-far {start 0}]
;;     (let [current (first (peek frontier))
;;           next (neighbours current graph)
;;           costs (reduce merge {} (next-cost cost-so-far current next))]
;;       (println came-from cost-so-far)
;;       (if (empty? frontier)
;;         {:from came-from :cost cost-so-far}
;;         (recur (merge-with #(if (< %1 %2) %1 %2) (pop frontier) costs)
;;                (merge-min-cost-map came-from current cost-so-far costs)
;;                ;; (reduce #(assoc %1 %2 current) came-from (flatten (map keys next)))
;;                (merge-with #(if (< %1 %2) %1 %2) frontier costs))))))


(defn visit-neighbours [graph u neighbours distance previous queue]
  (loop [n neighbours
         dist distance
         prev previous
         q queue]
    (if (empty? n)
      {:distance dist :previous prev :queue q}
      (let [[v _] (first (first n))
            alt (+ (dist u) (common/get-cost-step graph u v))]
        (if (< alt (or (dist v) infinity))
          (recur (rest n) (assoc dist v alt) (assoc prev v u) (assoc q v alt))
          (recur (rest n) dist prev q))))))

(defn dijkstra-search [graph start _end]
  (loop [queue (priority-map start 0)
         distance {start 0}
         previous {start nil}]
    (if (empty? queue)
      {:distance distance :from previous}
    (let [current (first (peek queue))
          neighbours (neighbours current graph)
          next (visit-neighbours graph current neighbours distance previous (pop queue))]
      (recur (next :queue) (next :distance) (next :previous))))))


(defn path [graph start end f]
  (let [res (:from (f graph start end))]
    (loop [p (res end)
           t [end]]
      (if (= p start)
        (reverse (conj t start))
        (recur (res p)
               (conj t p))))))

(comment
  (path graph :A :G dijkstra-search))
