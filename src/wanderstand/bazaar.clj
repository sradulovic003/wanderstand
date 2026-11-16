(ns wanderstand.bazaar)

(defn valid-bazaar? [bazaar-data]
  (boolean (and (seq (:name bazaar-data))
                (seq (:city bazaar-data))
                (seq (:date bazaar-data)))))

(defn open-bazaar [bazaar-data]
  (if (valid-bazaar? bazaar-data)
    {:name (:name bazaar-data)
     :city (:city bazaar-data)
     :date (:date bazaar-data)
     :status :open
     :applicants []}
    nil))

(defn apply-to-bazaar [bazaar vendor-id]
  (if (= (:status bazaar) :open)
    (update bazaar :applicants conj vendor-id)
    nil))

(defn close-bazaar [bazaar]
  (if (= (:status bazaar) :open)
    (assoc bazaar :status :closed)
    bazaar))

(defn is-full? [bazaar max-vendors]
  (>= (count (:applicants bazaar)) max-vendors))

(defn remove-applicant [bazaar vendor-id]
  (update bazaar :applicants
          (fn [applicants]
            (vec (filter #(not= % vendor-id) applicants)))))