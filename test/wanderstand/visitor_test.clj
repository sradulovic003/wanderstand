(ns wanderstand.visitor-test
  (:require [midje.sweet :refer :all]
            [wanderstand.visitor :refer :all]
            [wanderstand.vendor :refer :all]))


(fact "Visitor can search stands by city"
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
        (count (search-stands stands "Beograd")) => 1
        (count (search-stands stands "Nis")) => 0))


