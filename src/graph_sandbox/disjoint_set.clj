(ns graph-sandbox.disjoint-set
  (:import [java.util ArrayList]))

;; https://en.wikipedia.org/wiki/Minimum_spanning_tree

(defn parent [set elt]
  (.get set elt))

(defn rank [set elt]
  )

(defn find-set
  "Finds the element `elt`"
  [set elt]
  (if (= (parent set elt) elt)
    elt
    (let [res (find-set set (parent set elt))]
      ;; Compression, cache the result
      (.set set elt res)
      res)))

(defn union
  "Union of the set that contains `i` and the set that contains `j`"
  [set i j]
  (assoc set (find-set set i) (find-set set j)))

(defn pp []
  (let [l (ArrayList. [4 2 2 3 3 2 7 2])]
    (find-set l 6)))
