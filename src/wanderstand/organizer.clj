(ns wanderstand.organizer)

(defn valid-bazaar? [bazaar-data]
  (and (not (empty? (:name bazaar-data)))
       (not (empty? (:city bazaar-data)))
       (not (empty? (:date bazaar-data)))))

(defn open-bazaar [bazaar-data]
  (if (valid-bazaar? bazaar-data)
    {:name (:name bazaar-data)
     :city (:city bazaar-data)
     :date (:date bazaar-data)
     :status :open
     :applicants []}
    nil))

(defn is-bazaar-full? [bazaar max-vendors]
  (>= (count (:applicants bazaar)) max-vendors))