(ns wanderstand.vendor-test
  (:require [midje.sweet :refer :all]
            [wanderstand.vendor :refer :all]))

(fact "Vendor can post a stand"
      (let [result (post-stand {:vendor-id 1
                                :address "Knez Mihailova 15"
                                :city "Beograd"
                                :category :jewelry
                                :from "09:00"
                                :to "18:00"})]
        (:vendor-id result) => 1
        (:address result) => "Knez Mihailova 15"
        (:city result) => "Beograd"
        (:category result) => :jewelry
        (:from result) => "09:00"
        (:to result) => "18:00"))

(fact "Stand with missing fields throws exception"
      (post-stand {:vendor-id nil :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "09:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo "Vendor id is required")
      (post-stand {:vendor-id 1 :address "" :city "Beograd" :category :jewelry :from "09:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo "Address is required")
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "" :category :jewelry :from "09:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo "City is required")
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category nil :from "09:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo "Category is required")
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "" :to "18:00"}) => (throws clojure.lang.ExceptionInfo "From time is required")
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "09:00" :to ""}) => (throws clojure.lang.ExceptionInfo "To time is required"))

(fact "Stand with invalid time range throws exception"
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "18:00" :to "09:00"}) => (throws clojure.lang.ExceptionInfo "From time must be before to time and cannot be equal")
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "09:00" :to "09:00"}) => (throws clojure.lang.ExceptionInfo "From time must be before to time and cannot be equal"))

(fact "Stand with invalid time format throws exception"
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "9:00" :to "18:00"}) => (throws clojure.lang.ExceptionInfo "From time must be in HH:MM format")
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "ab:cd" :to "18:00"}) => (throws clojure.lang.ExceptionInfo "From time must be in HH:MM format")
      (post-stand {:vendor-id 1 :address "Knez Mihailova 15" :city "Beograd" :category :jewelry :from "09:00" :to "25:00"}) => (throws clojure.lang.ExceptionInfo "To time must be in HH:MM format"))

(fact "Vendor can see their own stands"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stand-2 (post-stand {:vendor-id 2
                                 :address "Zmaj Jovina 3"
                                 :city "Novi Sad"
                                 :category :ceramics
                                 :from "10:00"
                                 :to "17:00"})
            stands [stand-1 stand-2]
            result (get-vendor-stands stands 1)]
        (count result) => 1
        (:address (first result)) => "Knez Mihailova 15"))

(fact "Vendor with no stands gets empty list"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stands [stand-1]]
        (get-vendor-stands stands 2) => []))

(fact "Get vendor stands without vendor id throws exception"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stands [stand-1]]
        (get-vendor-stands stands nil) => (throws clojure.lang.ExceptionInfo "Vendor id is required")))