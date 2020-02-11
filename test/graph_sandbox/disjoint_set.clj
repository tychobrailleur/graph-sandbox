(ns graph-sandbox.disjoint-set
  (:require [graph-sandbox.disjoint-set :as sut]
            [clojure.test :as t]))

(t/deftest test-find-set
  (t/testing "Returns the root of tree containing element"
    (t/is (= (sut/find-set [4 2 2 3 3 2 7 2] 6) 2))))
