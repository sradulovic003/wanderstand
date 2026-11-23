(ns wanderstand.bazaar-test
  (:require [midje.sweet :refer :all]
            [wanderstand.bazaar :refer :all]))

(fact "Organizer can open a bazaar"
      (let [bazaar-data {:name "Beograd Bazar"
                         :city "Beograd"
                         :date "2026-12-01"
                         :max-vendors 10}]
        (let [result (open-bazaar bazaar-data)]
          (:name result) => "Beograd Bazar"
          (:status result) => :open
          (:applicants result) => [])))

(fact "Bazaar without name is not valid"
      (open-bazaar {:name ""
                    :city "Beograd"
                    :date "2026-12-01"
                    :max-vendors 10}) => nil)

(fact "Organizer can close a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})]
        (:status (close-bazaar bazaar)) => :closed))

(fact "Vendor can apply to a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})]
        (:applicants (apply-to-bazaar bazaar 1)) => [1]))

(fact "Vendor cannot apply to a closed bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            closed-bazaar (close-bazaar bazaar)]
        (apply-to-bazaar closed-bazaar 1) => closed-bazaar))

(fact "Applicant can be removed from a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)]
        (:applicants (remove-applicant bazaar-with-applicant 1)) => []))

(fact "Bazaar has max vendors limit"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 2})]
        (:max-vendors bazaar) => 2))

(fact "Vendor cannot apply to a full bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 2})
            bazaar-with-two (-> bazaar
                                (apply-to-bazaar 1)
                                (apply-to-bazaar 2))]
        (apply-to-bazaar bazaar-with-two 3) => nil))