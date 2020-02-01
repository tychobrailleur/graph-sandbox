(ns graph-sandbox.dijkstra-test
  (:require [graph-sandbox.dijkstra :as sut]
            [clojure.test :as t]))

(t/deftest test-dijkstra-path
  (t/testing "Path :A to :G"
    (t/is (= (sut/path sut/graph :A :G sut/dijkstra-search) '(:A :B :F :G))))
  (t/testing "Path :A to :J"
    (t/is (= (sut/path sut/graph :A :J sut/dijkstra-search) '(:A :B :F :J))))
  (t/testing "Path :A to :G"
    (t/is (= (sut/path sut/graph :A :I sut/dijkstra-search) '(:A :B :D :H :I)))))
