(ns wanderstand.organizer)

(defn is-bazaar-full? [bazaar max-vendors]
  (>= (count (:applicants bazaar)) max-vendors))