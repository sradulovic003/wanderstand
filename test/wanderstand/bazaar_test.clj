(ns wanderstand.bazaar-test
  (:require [midje.sweet :refer :all]
            [wanderstand.bazaar :refer :all]))

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

(fact "Organizer can close a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"})]
        (:status (close-bazaar bazaar)) => :closed))

(fact "Vendor can apply to a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"})]
        (:applicants (apply-to-bazaar bazaar 1)) => [1]))

(fact "Vendor cannot apply to a closed bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"})
            closed-bazaar (close-bazaar bazaar)]
        (apply-to-bazaar closed-bazaar 1) => closed-bazaar))

(fact "Applicant can be removed from a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)]
        (:applicants (remove-applicant bazaar-with-applicant 1)) => []))