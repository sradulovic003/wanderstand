(ns wanderstand.organizer)

(defn accept-applicant [bazaar vendor-id]
  (cond
    (not= (:status bazaar) :open) (throw (ex-info "Bazaar is not open" {:type :invalid-operation-error}))
    (>= (count (:accepted-vendors bazaar)) (:max-vendors bazaar)) (throw (ex-info "Bazaar is full" {:type :invalid-operation-error}))
    (not (some (fn [id] (= id vendor-id)) (:applicants bazaar))) (throw (ex-info "Vendor is not in the applicants list" {:type :not-found-error}))
    :else (-> bazaar
              (update :accepted-vendors conj vendor-id)
              (update :applicants
                      (fn [applicants]
                        (vec (filter (fn [id] (not= id vendor-id)) applicants)))))))

(defn reject-applicant [bazaar vendor-id]
  (if (some (fn [id] (= id vendor-id)) (:applicants bazaar))
    (update bazaar :applicants
            (fn [applicants]
              (vec (filter (fn [id] (not= id vendor-id)) applicants))))
    (throw (ex-info "Vendor is not in the applicants list" {:type :not-found-error}))))