(ns wanderstand.bazaar)

(defn valid-bazaar? [bazaar-data]
  (boolean (and (seq (:name bazaar-data))
                (seq (:city bazaar-data))
                (seq (:date bazaar-data))
                (some? (:max-vendors bazaar-data)))))

(defn open-bazaar [bazaar-data]
  (if (valid-bazaar? bazaar-data)
    {:name (:name bazaar-data)
     :city (:city bazaar-data)
     :date (:date bazaar-data)
     :status :open
     :applicants []
     :max-vendors (:max-vendors bazaar-data)
     :accepted-vendors []}
    nil))

(defn is-full? [bazaar max-vendors]
  (>= (count (:applicants bazaar)) max-vendors))

(defn apply-to-bazaar [bazaar vendor-id]
  (cond
    (not= (:status bazaar) :open) bazaar
    (is-full? bazaar (:max-vendors bazaar)) nil
    :else (update bazaar :applicants conj vendor-id)))

(defn close-bazaar [bazaar]
  (if (= (:status bazaar) :open)
    (assoc bazaar :status :closed)
    bazaar))

(defn remove-applicant [bazaar vendor-id]
  (update bazaar :applicants
          (fn [applicants]
            (vec (filter #(not= % vendor-id) applicants)))))