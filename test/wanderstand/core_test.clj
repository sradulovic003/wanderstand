(ns wanderstand.core-test
  (:require [clojure.test :refer :all]
            [wanderstand.core :refer :all]))

(deftest test-post-stand
  (testing "Vendor posts a stand"
    (let [stand-data {:vendor-id 1
                      :address "Knez Mihailova 15"
                      :city "Beograd"
                      :category :jewelry
                      :from "09:00"
                      :to "18:00"}]
      (is (= (:vendor-id (post-stand stand-data)) 1))
      (is (= (:address (post-stand stand-data)) "Knez Mihailova 15"))
      (is (= (:city (post-stand stand-data)) "Beograd"))
      (is (= (:category (post-stand stand-data)) :jewelry))
      (is (= (:from (post-stand stand-data)) "09:00"))
      (is (= (:to (post-stand stand-data)) "18:00")))))


(deftest test-search-stands
  (testing "Visitor searches stands by city"
    (let [stands [(post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "09:00" :to "18:00"})
                  (post-stand {:vendor-id 2 :address "Zmaj Jovina 3" :city "Novi Sad" :category :ceramics :from "10:00" :to "17:00"})]]
      (is (= 1 (count (search-stands stands "Beograd"))))
      (is (= 0 (count (search-stands stands "Nis")))))))



(deftest test-apply-to-bazaar
  (testing "Vendor applies to a bazaar"
    (let [bazaar {:name "Beograd Bazar"
                  :applicants []}]
      (let [result (apply-to-bazaar bazaar 1)]
        (is (= [1] (:applicants result)))))))