(ns wanderstand.visitor)

(defn search-stands [stands city]
  (let [result (filter (fn [stand]
                         (= (:city stand) city))
                       stands)]
    (cond
      (not (seq city)) (throw (ex-info "City is required" {:type :validation-error}))
      :else result)))

(defn search-stands-by-category [stands category]
  (let [result (filter (fn [stand]
                         (= (:category stand) category))
                       stands)]
    (cond
      (nil? category) (throw (ex-info "Category is required" {:type :validation-error}))
      :else result)))

(defn get-stand-details [stands stand-id]
  (let [result (first (filter (fn [stand]
                                (= (:id stand) stand-id))
                              stands))]
    (cond
      (nil? stand-id) (throw (ex-info "Stand id is required" {:type :validation-error}))
      (nil? result) (throw (ex-info "Stand not found" {:type :not-found-error}))
      :else result)))