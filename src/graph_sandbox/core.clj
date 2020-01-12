(ns graph-sandbox.core)

;; Implementation of the different algorithms described here:
;; https://www.redblobgames.com/pathfinding/a-star/introduction.html
;;
;; Also interesting link:
;; http://dnaeon.github.io/graphs-and-clojure/
;; from where I took the adjacency list representation.

(def graph {:A [:B :C]
            :B [:A :X]
            :X [:B :Y]
            :Y [:X]
            :C [:A :D]
            :D [:C :E :F]
            :E [:D :G]
            :F [:D :G]
            :G [:E :F]})


(defn visited? [node visited]
  (contains? (set visited) node))

(defn neighbours [node graph]
  (graph node))

(defn next-frontier [graph frontier visited]
  (into #{} (filter (complement #(visited? % visited))
                    (flatten (map #(neighbours % graph) frontier)))))

(defn breadth-first [graph start f]
  (loop [visited #{start}
         frontier #{start}]
    (if (empty? frontier)
      visited
      (let [next (next-frontier graph frontier visited)]
        (f next)
        (recur (concat visited next) next)))))

(defn breadth-first-paths [graph start f]
  (loop [visited #{start}
         frontier #{start}
         came-from {start nil}]
    (if (empty? frontier)
      {:visited visited :from came-from}
      (let [cur (first frontier)
            next (filter (complement #(visited? % visited)) (distinct (neighbours cur graph)))]
        (f cur)
        (recur (concat visited next)
               (distinct (concat (rest frontier) next))
               (reduce #(assoc %1 %2 cur) came-from next))))))

(defn path [graph start end]
  (let [steps (:from (breadth-first-paths graph start identity))]
    (loop [current end
           path [end]]
      (if (= current start)
        (reverse path)
        (recur (steps current)
               (conj path (steps current)))))))


(defn run-breadth-first []
  (breadth-first graph :A println))

(defn run-breadth-first-with-paths []
  (breadth-first-paths graph :A println))

(defn run-breadth-first-find-path []
  (path graph :A :G))




(breadth-first graph :A println)

(breadth-first-paths graph :A println)
