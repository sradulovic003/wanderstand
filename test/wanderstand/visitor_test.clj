(ns wanderstand.visitor-test
  (:require [midje.sweet :refer :all]
            [wanderstand.visitor :refer :all]
            [wanderstand.vendor :refer :all]))

(fact "Visitor can search stands by city"
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
            result (search-stands stands "Beograd")]
        (count result) => 1
        (:address (first result)) => "Knez Mihailova 15"))

(fact "Search stands without city throws exception"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stands [stand-1]]
        (search-stands stands "") => (throws clojure.lang.ExceptionInfo "City is required")
        (search-stands stands nil) => (throws clojure.lang.ExceptionInfo "City is required")))

(fact "Search stands in city with no stands returns empty list"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stands [stand-1]]
        (search-stands stands "Nis") => []))

(fact "Visitor can search stands by category"
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
            result (search-stands-by-category stands :jewelry)]
        (count result) => 1
        (:category (first result)) => :jewelry))

(fact "Search stands without category throws exception"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stands [stand-1]]
        (search-stands-by-category stands nil) => (throws clojure.lang.ExceptionInfo "Category is required")))

(fact "Search stands in category with no stands returns empty list"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stands [stand-1]]
        (search-stands-by-category stands :ceramics) => []))

(fact "Visitor can get stand details"
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
            result (get-stand-details stands 1)]
        (:address result) => "Knez Mihailova 15"
        (:city result) => "Beograd"))

(fact "Get stand details without vendor id throws exception"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stands [stand-1]]
        (get-stand-details stands nil) => (throws clojure.lang.ExceptionInfo "Vendor id is required")))

(fact "Get details of non-existing stand throws exception"
      (let [stand-1 (post-stand {:vendor-id 1
                                 :address "Knez Mihailova 15"
                                 :city "Beograd"
                                 :category :jewelry
                                 :from "09:00"
                                 :to "18:00"})
            stands [stand-1]]
        (get-stand-details stands 5) => (throws clojure.lang.ExceptionInfo "Stand not found")))