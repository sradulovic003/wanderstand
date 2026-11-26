(ns wanderstand.bazaar)

(defn valid-bazaar? [bazaar-data]
  (cond
    (not (seq (:name bazaar-data))) (throw (ex-info "Bazaar name is required" {:type :validation-error}))
    (not (seq (:city bazaar-data))) (throw (ex-info "Bazaar city is required" {:type :validation-error}))
    (not (seq (:date bazaar-data))) (throw (ex-info "Bazaar date is required" {:type :validation-error}))
    (nil? (:max-vendors bazaar-data)) (throw (ex-info "Max vendors is required" {:type :validation-error}))
    (<= (:max-vendors bazaar-data) 0) (throw (ex-info "Max vendors must be greater than zero" {:type :validation-error}))
    :else true))

(defn open-bazaar [bazaar-data]
  (valid-bazaar? bazaar-data)
  {:name (:name bazaar-data)
   :city (:city bazaar-data)
   :date (:date bazaar-data)
   :max-vendors (:max-vendors bazaar-data)
   :status :open
   :applicants []
   :accepted-vendors []})

(defn is-full? [bazaar max-vendors]
  (>= (count (:applicants bazaar)) max-vendors))

(defn apply-to-bazaar [bazaar vendor-id]
  (cond
    (not= (:status bazaar) :open) (throw (ex-info "Bazaar is not open" {:type :invalid-operation-error}))
    (is-full? bazaar (:max-vendors bazaar)) (throw (ex-info "Bazaar is full" {:type :invalid-operation-error}))
    :else (update bazaar :applicants conj vendor-id)))

(defn close-bazaar [bazaar]
  (if (= (:status bazaar) :open)
    (assoc bazaar :status :closed)
    (throw (ex-info "Bazaar is already closed" {:type :invalid-operation-error}))))

(defn remove-applicant [bazaar vendor-id]
  (if (some (fn [id] (= id vendor-id)) (:applicants bazaar))
    (update bazaar :applicants
            (fn [applicants]
              (vec (filter (fn [id] (not= id vendor-id)) applicants))))
    (throw (ex-info "Vendor is not in the applicants list" {:type :not-found-error}))))