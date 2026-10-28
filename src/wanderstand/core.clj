(ns wanderstand.core)

(defn post-stand [stand-data]
  { :vendor-id (:vendor-id stand-data)
    :location (:location stand-data)
    :category (:category stand-data)
    :from (:from stand-data)
    :to (:to stand-data)
   }
  )



