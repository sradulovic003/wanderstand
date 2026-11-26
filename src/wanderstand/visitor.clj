(ns wanderstand.visitor)

(defn search-stands [stands city]
  (let [result (filter (fn [stand]
                         (= (:city stand) city))
                       stands)]
    (cond
      (not (seq city)) (throw (ex-info "City is required" {:type :validation-error}))
      (empty? result) (throw (ex-info "No stands found in this city" {:type :not-found-error}))
      :else result)))

(defn search-stands-by-category [stands category]
  (let [result (filter (fn [stand]
                         (= (:category stand) category))
                       stands)]
    (cond
      (nil? category) (throw (ex-info "Category is required" {:type :validation-error}))
      (empty? result) (throw (ex-info "No stands found in this category" {:type :not-found-error}))
      :else result)))

(defn get-stand-details [stands vendor-id]
  (let [result (first (filter (fn [stand]
                                (= (:vendor-id stand) vendor-id))
                              stands))]
    (cond
      (nil? vendor-id) (throw (ex-info "Vendor id is required" {:type :validation-error}))
      (nil? result) (throw (ex-info "Stand not found" {:type :not-found-error}))
      :else result)))