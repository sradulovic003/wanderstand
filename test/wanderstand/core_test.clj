(ns wanderstand.core-test
  (:require [clojure.test :refer :all]
            [wanderstand.core :refer :all]))

(deftest test-post-stand
  (testing "Vendor posts a stand"
    (let [stand-data {:vendor-id 1
                      :location "Knez Mihailova 15, Beograd"
                      :category :jewelry
                      :from "09:00"
                      :to "18:00"}]
      (is (= (:vendor-id (post-stand stand-data)) 1))
      (is (= (:location (post-stand stand-data)) "Knez Mihailova 15, Beograd"))
      (is (= (:category (post-stand stand-data)) :jewelry))
      (is (= (:from (post-stand stand-data)) "09:00"))
      (is (= (:to (post-stand stand-data)) "18:00")))))
