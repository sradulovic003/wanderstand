(ns wanderstand.organizer-test
  (:require [midje.sweet :refer :all]
            [wanderstand.organizer :refer :all]
            [wanderstand.bazaar :refer :all]))

(fact "Organizer can accept an applicant"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)]
        (:accepted-vendors (accept-applicant bazaar-with-applicant 1)) => [1]))

(fact "Organizer can reject an applicant"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)]
        (:applicants (reject-applicant bazaar-with-applicant 1)) => []))