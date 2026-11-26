(ns wanderstand.store
  (:require [wanderstand.bazaar :as bazaar]
            [wanderstand.vendor :as vendor]))

(def store (atom {:bazaars []
                  :stands []}))

(defn next-id [items]
  (inc (count items)))

(defn get-all-bazaars []
  (:bazaars @store))

(defn add-bazaar! [bazaar-data]
  (let [id (next-id (:bazaars @store))
        new-bazaar (bazaar/open-bazaar (assoc bazaar-data :id id))]
    (swap! store update :bazaars conj new-bazaar)
    new-bazaar))

(defn find-bazaar [id]
  (first (filter (fn [bazaar]
                   (= (:id bazaar) id))
                 (:bazaars @store))))

(defn add-stand! [stand-data]
  (let [id (next-id (:stands @store))
        new-stand (vendor/post-stand (assoc stand-data :id id))]
    (swap! store update :stands conj new-stand)
    new-stand))

(defn get-all-stands []
  (:stands @store))

(defn find-stand [id]
  (first (filter (fn [stand]
                   (= (:id stand) id))
                 (:stands @store))))

(defn reset-store! []
  (reset! store {:bazaars []
                 :stands []}))
