(ns graph-sandbox.dijkstra-test
  (:require [graph-sandbox.dijkstra :as sut]
            [clojure.test :as t]))

;; (t/deftest test-merge-mn-map
;;   (t/testing "Other doesn't have value"
;;     (t/is (= (sut/merge-min-map {:a 1 :b 2} {:c 3}) {:a 1 :b 2 :c 3})))
;;   (t/testing "Other has smaller value"
;;     (t/is (= (sut/merge-min-map {:a 3 :b 2} {:b 1}) {:a 3 :b 1})))
;;   (t/testing "This one has smaller value"
;;     (t/is (= (sut/merge-min-map {:a 3 :b 1} {:b 3}) {:a 3 :b 1})))
;;   (t/testing "Either has smaller value"
;;     (t/is (= (sut/merge-min-map {:a 3 :b 2} {:b 3 :a 1}) {:a 1 :b 2}))))

;; (t/deftest test-merge-from-map
;;   (t/testing "New cost is higher"
;;     (t/is (= (sut/merge-min-cost-map {:B :A :C :F} :D {:B 1} {:B 2}) {:B :A :C :F})))
;;   (t/testing "New cost is lower"
;;     (t/is (= (sut/merge-min-cost-map {:B :A :C :F} :D {:B 3} {:B 1}) {:B :D :C :F})))
;;   (t/testing "Cost is new"
;;     (t/is (= (sut/merge-min-cost-map {:B :A :C :F} :D {} {:B 1}) {:B :D :C :F}))))

(t/deftest test-dijkstra-path
  (t/testing "Path :A to :G"
    (t/is (= (sut/path sut/graph :A :G sut/dijkstra-search) '(:A :B :F :G))))
  (t/testing "Path :A to :J"
    (t/is (= (sut/path sut/graph :A :J sut/dijkstra-search) '(:A :B :F :J))))
  (t/testing "Path :A to :G"
    (t/is (= (sut/path sut/graph :A :I sut/dijkstra-search) '(:A :B :D :H :I)))))
