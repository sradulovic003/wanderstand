(ns wanderstand.organizer)

(defn accept-applicant [bazaar vendor-id]
  (update bazaar :accepted-vendors conj vendor-id))

(defn reject-applicant [bazaar vendor-id]
  (update bazaar :applicants
          (fn [applicants]
            (vec (filter #(not= % vendor-id) applicants)))))