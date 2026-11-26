(ns wanderstand.vendor
  (:import [java.time LocalTime]))

(defn valid-time-format? [time]
  (try
    (LocalTime/parse time)
    true
    (catch Exception e
      false)))

(defn valid-time-range? [from to]
  (let [from-time (LocalTime/parse from)
        to-time (LocalTime/parse to)]
    (.isBefore from-time to-time)))

(defn valid-stand? [stand-data]
  (cond
    (nil? (:vendor-id stand-data)) (throw (ex-info "Vendor id is required" {:type :validation-error}))
    (not (seq (:city stand-data))) (throw (ex-info "City is required" {:type :validation-error}))
    (not (seq (:address stand-data))) (throw (ex-info "Address is required" {:type :validation-error}))
    (nil? (:category stand-data)) (throw (ex-info "Category is required" {:type :validation-error}))
    (not (seq (:from stand-data))) (throw (ex-info "From time is required" {:type :validation-error}))
    (not (seq (:to stand-data))) (throw (ex-info "To time is required" {:type :validation-error}))
    (not (valid-time-format? (:from stand-data))) (throw (ex-info "From time must be in HH:MM format" {:type :validation-error}))
    (not (valid-time-format? (:to stand-data))) (throw (ex-info "To time must be in HH:MM format" {:type :validation-error}))
    (not (valid-time-range? (:from stand-data) (:to stand-data))) (throw (ex-info "From time must be before to time and cannot be equal" {:type :validation-error}))
    :else true))

(defn post-stand [stand-data]
  (valid-stand? stand-data)
  {:vendor-id (:vendor-id stand-data)
   :address (:address stand-data)
   :city (:city stand-data)
   :category (:category stand-data)
   :from (:from stand-data)
   :to (:to stand-data)})

(defn get-vendor-stands [stands vendor-id]
  (let [result (filter (fn [stand]
                         (= (:vendor-id stand) vendor-id))
                       stands)]
    (cond
      (nil? vendor-id) (throw (ex-info "Vendor id is required" {:type :validation-error}))
      :else result)))
