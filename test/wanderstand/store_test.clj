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

(fact "Bazaars get ids and can be found by id"
      (reset-store!)
      (add-bazaar! {:name "Beograd Bazar"
                    :city "Beograd"
                    :date "2026-12-01"
                    :max-vendors 10})
      (add-bazaar! {:name "Novi Sad Bazar"
                    :city "Novi Sad"
                    :date "2026-12-05"
                    :max-vendors 5})
      (:name (find-bazaar 1)) => "Beograd Bazar"
      (:name (find-bazaar 2)) => "Novi Sad Bazar"
      (find-bazaar 3) => nil)

(fact "Stand can be added to store"
      (reset-store!)
      (add-stand! {:vendor-id 1
                   :address "Knez Mihailova 15"
                   :city "Beograd"
                   :category :jewelry
                   :from "09:00"
                   :to "18:00"})
      (count (get-all-stands)) => 1
      (:city (first (get-all-stands))) => "Beograd"
      (:id (first (get-all-stands))) => 1
      (:address (find-stand 1)) => "Knez Mihailova 15")
