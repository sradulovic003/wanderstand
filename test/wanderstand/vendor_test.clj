(ns wanderstand.vendor-test
  (:require [midje.sweet :refer :all]
            [wanderstand.vendor :refer :all]
            [wanderstand.bazaar :refer :all]))

(fact "Vendor can post a stand"
      (let [stand-data {:vendor-id 1
                        :address "Knez Mihailova 15"
                        :city "Beograd"
                        :category :jewelry
                        :from "09:00"
                        :to "18:00"}]
        (let [result (post-stand stand-data)]
          (:city result) => "Beograd"
          (:address result) => "Knez Mihailova 15"
          (:from result) => "09:00"
          (:to result) => "18:00")))

(fact "Stand with missing fields throws exception"
      (valid-stand? {:vendor-id nil :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "09:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo)
      (valid-stand? {:vendor-id 1 :address "" :city "Beograd" :category :jewelry :from "09:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo)
      (valid-stand? {:vendor-id 1 :address "Knez Mihailova 15" :city "" :category :jewelry :from "09:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo)
      (valid-stand? {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category nil :from "09:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo)
      (valid-stand? {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "" :to "18:00"}) => (throws clojure.lang.ExceptionInfo)
      (valid-stand? {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "09:00" :to ""}) => (throws clojure.lang.ExceptionInfo))

(fact "Stand with invalid time range throws exception"
      (valid-stand? {:vendor-id 1
                     :address "Knez Mihailova 15"
                     :city "Beograd"
                     :category :jewelry
                     :from "18:00"
                     :to "09:00"}) => (throws clojure.lang.ExceptionInfo "From time must be before to time and cannot be equal"))

(fact "Vendor can apply to a bazaar"
      (let [bazaar {:name "Beograd Bazar"
                    :status :open
                    :max-vendors 10
                    :applicants []}]
        (let [result (apply-to-bazaar bazaar 1)]
          (:applicants result) => [1])))

(fact "Vendor cannot apply to a closed bazaar"
      (let [bazaar {:name "Beograd Bazar"
                    :status :closed
                    :max-vendors 10
                    :applicants []}]
        (apply-to-bazaar bazaar 1) => (throws clojure.lang.ExceptionInfo "Bazaar is not open")))

(fact "Vendor can see their own stands"
      (let [stands [(post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
                    (post-stand {:vendor-id 2
                                 :address "Zmaj Jovina 3"
                                 :city "Novi Sad"
                                 :category :ceramics
                                 :from "10:00"
                                 :to "17:00"})]]
        (count (get-vendor-stands stands 1)) => 1
        (:address (first (get-vendor-stands stands 1))) => "Knez Mihailova 15"))

(fact "No stands found for vendor throws exception"
      (get-vendor-stands [] 1) => (throws clojure.lang.ExceptionInfo "No stands found for this vendor"))
