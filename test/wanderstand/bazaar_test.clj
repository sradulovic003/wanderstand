(ns wanderstand.bazaar-test
  (:require [midje.sweet :refer :all]
            [wanderstand.bazaar :refer :all]))

(fact "Organizer can open a bazaar"
      (let [result (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})]
        (:name result) => "Beograd Bazar"
        (:city result) => "Beograd"
        (:date result) => "2026-12-01"
        (:max-vendors result) => 10
        (:status result) => :open
        (:applicants result) => []
        (:accepted-vendors result) => []))

(fact "Bazaar with missing fields throws exception"
      (open-bazaar {:name "" :city "Beograd" :date "2026-12-01" :max-vendors 10}) => (throws clojure.lang.ExceptionInfo "Bazaar name is required")
      (open-bazaar {:name "Beograd Bazar" :city "" :date "2026-12-01" :max-vendors 10}) => (throws clojure.lang.ExceptionInfo "Bazaar city is required")
      (open-bazaar {:name "Beograd Bazar" :city "Beograd" :date "" :max-vendors 10}) => (throws clojure.lang.ExceptionInfo "Bazaar date is required")
      (open-bazaar {:name "Beograd Bazar" :city "Beograd" :date "2026-12-01" :max-vendors nil}) => (throws clojure.lang.ExceptionInfo "Max vendors is required")
      (open-bazaar {:name "Beograd Bazar" :city "Beograd" :date "2026-12-01" :max-vendors 0}) => (throws clojure.lang.ExceptionInfo "Max vendors must be greater than zero"))

(fact "Organizer can close a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            result (close-bazaar bazaar)]
        (:status result) => :closed))

(fact "Closing already closed bazaar throws exception"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            closed-bazaar (close-bazaar bazaar)]
        (close-bazaar closed-bazaar) => (throws clojure.lang.ExceptionInfo "Bazaar is already closed")))

(fact "Vendor can apply to a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            result (apply-to-bazaar bazaar 1)]
        (:applicants result) => [1]))

(fact "Vendor cannot apply to a closed bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            closed-bazaar (close-bazaar bazaar)]
        (apply-to-bazaar closed-bazaar 1) => (throws clojure.lang.ExceptionInfo "Bazaar is not open")))

(fact "Vendor cannot apply to a full bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 2})
            bazaar-with-two (-> bazaar
                                (apply-to-bazaar 1)
                                (apply-to-bazaar 2))]
        (apply-to-bazaar bazaar-with-two 3) => (throws clojure.lang.ExceptionInfo "Bazaar is full")))

(fact "Applicant can be removed from a bazaar"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)
            result (remove-applicant bazaar-with-applicant 1)]
        (:applicants result) => []))

(fact "Removing non-existing applicant throws exception"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})]
        (remove-applicant bazaar 1) => (throws clojure.lang.ExceptionInfo "Vendor is not in the applicants list")))