(ns wanderstand.vendor)


(defn valid-stand? [stand-data]
  (and (not (nil? (:vendor-id stand-data)))
       (not (empty? (:city stand-data)))
       (not (empty? (:address stand-data)))
       (not (nil? (:category stand-data)))
       (not (empty? (:from stand-data)))
       (not (empty? (:to stand-data)))))

(defn post-stand [stand-data]
  (if (valid-stand? stand-data)
    { :vendor-id (:vendor-id stand-data)
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


