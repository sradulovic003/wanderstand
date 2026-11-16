(ns wanderstand.store
  (:require [wanderstand.bazaar :as bazaar]
            [wanderstand.vendor :as vendor]))

(def store (atom {:bazaars []
                  :stands []}))

(defn get-all-bazaars []
  (:bazaars @store))

(defn add-bazaar! [bazaar-data]
  (let [new-bazaar (bazaar/open-bazaar bazaar-data)]
    (if new-bazaar
      (swap! store update :bazaars conj new-bazaar)
      nil)))

(defn find-bazaar [name]
  (first (filter (fn [bazaar]
                   (= (:name bazaar) name))
                 (:bazaars @store))))

(defn add-stand! [stand-data]
  (let [new-stand (vendor/post-stand stand-data)]
    (if new-stand
      (swap! store update :stands conj new-stand)
      nil)))

(defn get-all-stands []
  (:stands @store))

(defn reset-store! []
  (reset! store {:bazaars []
                 :stands []}))
