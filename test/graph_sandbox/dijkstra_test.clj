(ns graph-sandbox.dijkstra-test
  (:require [graph-sandbox.dijkstra :as sut]
            [clojure.test :as t]))

(t/deftest test-merge-mn-map
  (t/testing "Other doesn't have value"
    (t/is (= (sut/merge-min-map {:a 1 :b 2} {:c 3}) {:a 1 :b 2 :c 3})))
  (t/testing "Other has smaller value"
    (t/is (= (sut/merge-min-map {:a 3 :b 2} {:b 1}) {:a 3 :b 1})))
  (t/testing "This one has smaller value"
    (t/is (= (sut/merge-min-map {:a 3 :b 1} {:b 3}) {:a 3 :b 1})))
  (t/testing "Either has smaller value"
    (t/is (= (sut/merge-min-map {:a 3 :b 2} {:b 3 :a 1}) {:a 1 :b 2}))))
