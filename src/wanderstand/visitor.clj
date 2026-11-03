(ns wanderstand.visitor)


(defn search-stands [stands city]
  (filter (fn [stand]
            (= (:city stand) city))
          stands))

(defn search-stands-by-category [stands category]
  (filter (fn [stand]
            (= (:category stand) category))
          stands))

(defn get-stand-details [stands vendor-id]
  (first (filter (fn [stand]
                   (= (:vendor-id stand) vendor-id))
                 stands)))
