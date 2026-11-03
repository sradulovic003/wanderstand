(ns wanderstand.organizer-test
  (:require [midje.sweet :refer :all]))
(ns wanderstand.organizer-test
  (:require [midje.sweet :refer :all]
            [wanderstand.organizer :refer :all]))

(fact "Organizer can open a bazaar"
      (let [bazaar-data {:name "Beograd Bazar"
                         :city "Beograd"
                         :date "2026-12-01"}]
        (let [result (open-bazaar bazaar-data)]
          (:name result) => "Beograd Bazar"
          (:status result) => :open
          (:applicants result) => [])))

(fact "Bazaar without name is not valid"
      (open-bazaar {:name ""
                    :city "Beograd"
                    :date "2026-12-01"}) => nil)

(fact "Bazaar is full when max vendors reached"
      (let [bazaar {:name "Beograd Bazar"
                    :applicants [1 2 3]}]
        (is-bazaar-full? bazaar 3) => true
        (is-bazaar-full? bazaar 5) => false))