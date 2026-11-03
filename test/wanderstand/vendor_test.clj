(ns wanderstand.vendor-test
  (:require [midje.sweet :refer :all]
            [wanderstand.vendor :refer :all]))


(fact "Vendor can post a stand"
      (let [stand-data {:vendor-id 1
                        :address "Knez Mihailova 15"
                        :city "Beograd"
                        :category :jewelry
                        :from "09:00"
                        :to "18:00"}]
        (let [result (post-stand stand-data)]
          (:city result) => "Beograd"
          (:from result) => "09:00"
          (:to result) => "18:00")))


(fact "Vendor can apply to a bazaar"
      (let [bazaar {:name "Beograd Bazar"
                    :status :open
                    :applicants []}]
        (let [result (apply-to-bazaar bazaar 1)]
          (:applicants result) => [1])))
