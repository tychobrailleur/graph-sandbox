(ns graph-sandbox.common)

(def infinity (Double/POSITIVE_INFINITY))

(defn neighbours [node graph]
  (graph node))

(defn get-cost-step [graph from to]
  (->> (graph from)
       (some #(when (contains? % to) %))
       to))


(defn visited?
  "Checks whether node has already been visited."
  [node visited]
  (contains? (set visited) node))

(defn path
  "Computes the search algorithm defined by f, and returns the path found."
  [graph start end f]
  (let [res (:from (f graph start end))]
    (loop [p (res end)
           t [end]]
      (if (= p start)
        (reverse (conj t start))
        (recur (res p)
               (conj t p))))))


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
