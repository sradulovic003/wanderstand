(ns wanderstand.store-test
  (:require [midje.sweet :refer :all]
            [wanderstand.store :refer :all]))

(fact "Store is empty at the start"
      (reset-store!)
      (count (get-all-bazaars)) => 0
      (count (get-all-stands)) => 0)

(fact "Bazaar can be added to store"
      (reset-store!)
      (add-bazaar! {:name "Beograd Bazar"
                    :city "Beograd"
                    :date "2026-12-01"
                    :max-vendors 10})
      (count (get-all-bazaars)) => 1
      (:name (first (get-all-bazaars))) => "Beograd Bazar")

(fact "Invalid bazaar cannot be added to store"
      (reset-store!)
      (add-bazaar! {:name ""
                    :city "Beograd"
                    :date "2026-12-01"
                    :max-vendors 10}) => (throws clojure.lang.ExceptionInfo "Bazaar name is required"))

(fact "Bazaar can be found by name"
      (reset-store!)
      (add-bazaar! {:name "Beograd Bazar"
                    :city "Beograd"
                    :date "2026-12-01"
                    :max-vendors 10})
      (:name (find-bazaar "Beograd Bazar")) => "Beograd Bazar"
      (find-bazaar "Novi Sad Bazar") => nil)

(fact "Stand can be added to store"
      (reset-store!)
      (add-stand! {:vendor-id 1
                   :address "Knez Mihailova 15"
                   :city "Beograd"
                   :category :jewelry
                   :from "09:00"
                   :to "18:00"})
      (count (get-all-stands)) => 1
      (:city (first (get-all-stands))) => "Beograd")
