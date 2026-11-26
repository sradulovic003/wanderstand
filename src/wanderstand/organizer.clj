(ns wanderstand.organizer
  (:require [wanderstand.bazaar :refer [remove-applicant]]))

(defn accept-applicant [bazaar vendor-id]
  (cond
    (not= (:status bazaar) :open) (throw (ex-info "Bazaar is not open" {:type :invalid-operation-error}))
    (>= (count (:accepted-vendors bazaar)) (:max-vendors bazaar)) (throw (ex-info "Bazaar is full" {:type :invalid-operation-error}))
    (not (some (fn [id] (= id vendor-id)) (:applicants bazaar))) (throw (ex-info "Vendor is not in the applicants list" {:type :not-found-error}))
    :else (let [bazaar-with-accepted (update bazaar :accepted-vendors conj vendor-id)]
            (remove-applicant bazaar-with-accepted vendor-id))))

(defn reject-applicant [bazaar vendor-id]
  (remove-applicant bazaar vendor-id))