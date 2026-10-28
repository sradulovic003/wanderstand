(ns wanderstand.core)

(defn post-stand [stand-data]
  { :vendor-id (:vendor-id stand-data)
    :address (:address stand-data)
    :city (:city stand-data)
    :category (:category stand-data)
    :from (:from stand-data)
    :to (:to stand-data)}
  )


(defn search-stands [stands city]
  (filter (fn [stand]
            (= (:city stand) city))
    stands))


(defn apply-to-bazaar [bazaar vendor-id]
  (update bazaar :applicants conj vendor-id))




