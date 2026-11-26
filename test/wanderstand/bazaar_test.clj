(ns wanderstand.bazaar-test
  (:require [midje.sweet :refer :all]
            [wanderstand.bazaar :refer :all]
            [wanderstand.organizer :refer [accept-applicant]]))

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

(fact "Bazaar with invalid date throws exception"
      (open-bazaar {:name "Beograd Bazar" :city "Beograd" :date "01.12.2026" :max-vendors 10}) => (throws clojure.lang.ExceptionInfo "Bazaar date must be in YYYY-MM-DD format")
      (open-bazaar {:name "Beograd Bazar" :city "Beograd" :date "2026-13-01" :max-vendors 10}) => (throws clojure.lang.ExceptionInfo "Bazaar date must be in YYYY-MM-DD format"))

(fact "Max vendors must be a number"
      (open-bazaar {:name "Beograd Bazar" :city "Beograd" :date "2026-12-01" :max-vendors "10"}) => (throws clojure.lang.ExceptionInfo "Max vendors must be a number"))

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
            bazaar-with-one (apply-to-bazaar bazaar 1)
            bazaar-with-two (apply-to-bazaar bazaar-with-one 2)
            bazaar-accepted-1 (accept-applicant bazaar-with-two 1)
            bazaar-accepted-2 (accept-applicant bazaar-accepted-1 2)]
        (apply-to-bazaar bazaar-accepted-2 3) => (throws clojure.lang.ExceptionInfo "Bazaar is full")))

(fact "More vendors can apply than there are places"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 1})
            bazaar-with-one (apply-to-bazaar bazaar 1)
            result (apply-to-bazaar bazaar-with-one 2)]
        (:applicants result) => [1 2]))

(fact "Vendor cannot apply twice"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)]
        (apply-to-bazaar bazaar-with-applicant 1) => (throws clojure.lang.ExceptionInfo "Vendor has already applied")))

(fact "Accepted vendor cannot apply again"
      (let [bazaar (open-bazaar {:name "Beograd Bazar"
                                 :city "Beograd"
                                 :date "2026-12-01"
                                 :max-vendors 10})
            bazaar-with-applicant (apply-to-bazaar bazaar 1)
            bazaar-with-accepted (accept-applicant bazaar-with-applicant 1)]
        (apply-to-bazaar bazaar-with-accepted 1) => (throws clojure.lang.ExceptionInfo "Vendor is already accepted")))

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