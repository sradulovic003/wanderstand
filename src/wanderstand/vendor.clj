(ns wanderstand.vendor)

(defn valid-stand? [stand-data]
  (boolean (and (some? (:vendor-id stand-data))
                (seq (:city stand-data))
                (seq (:address stand-data))
                (some? (:category stand-data))
                (seq (:from stand-data))
                (seq (:to stand-data)))))

(defn post-stand [stand-data]
  (if (valid-stand? stand-data)
    {:vendor-id (:vendor-id stand-data)
     :address (:address stand-data)
     :city (:city stand-data)
     :category (:category stand-data)
     :from (:from stand-data)
     :to (:to stand-data)}
    nil))

(defn apply-to-bazaar [bazaar vendor-id]
  (if (= (:status bazaar) :open)
    (update bazaar :applicants conj vendor-id)
    nil))


