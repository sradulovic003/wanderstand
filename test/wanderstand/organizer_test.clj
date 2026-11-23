(ns wanderstand.organizer-test
  (:require [midje.sweet :refer :all]
            [wanderstand.organizer :refer :all]
            [wanderstand.bazaar :refer :all]))

(fact "Organizer can accept an applicant"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)
            result (accept-applicant bazaar-with-applicant 1)]
        (:accepted-vendors result) => [1]
        (:applicants result) => []))

(fact "Organizer can reject an applicant"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)]
        (:applicants (reject-applicant bazaar-with-applicant 1)) => []))

(fact "Cannot accept vendor in closed bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)
            closed-bazaar (close-bazaar bazaar-with-applicant)]
        (accept-applicant closed-bazaar 1) => (throws clojure.lang.ExceptionInfo "Bazaar is not open")))

(fact "Cannot accept vendor when bazaar is full"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 2})
            bazaar-with-two (-> bazaar
                                (apply-to-bazaar 1)
                                (apply-to-bazaar 2))
            bazaar-accepted (accept-applicant bazaar-with-two 1)
            bazaar-accepted-2 (accept-applicant bazaar-accepted 2)]
        (accept-applicant bazaar-accepted-2 3) => (throws clojure.lang.ExceptionInfo "Bazaar is full")))

(fact "Cannot accept vendor not in applicants list"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})]
        (accept-applicant bazaar 1) => (throws clojure.lang.ExceptionInfo "Vendor is not in the applicants list")))

(fact "Cannot reject vendor not in applicants list"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})]
        (reject-applicant bazaar 1) => (throws clojure.lang.ExceptionInfo "Vendor is not in the applicants list")))