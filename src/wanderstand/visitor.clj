(ns wanderstand.visitor)


(defn search-stands [stands city]
  (filter (fn [stand]
            (= (:city stand) city))
          stands))
