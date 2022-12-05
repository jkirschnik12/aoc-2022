(ns aoc-2022.day4
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.set :as set]
            [aoc-2022.utils :as u]
            [clojure.java.io :as io]))


(def sample "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8")

(defn rng-cnt?
  [[x y]]
  (or (set/subset? x y)
      (set/subset? y x)))

(defn rng-cnt-2?
  [[x y]]
  (seq (set/intersection x y)))

(defn solve
  [input]
  (let [range-pairs (->> input
                         (str/split-lines)
                         (map #(str/split % #","))
                         (map #(map (fn [pair]
                                      (let [[l u] (str/split pair #"-")
                                            rng (range (edn/read-string l) (inc (edn/read-string u)))]
                                        (set rng))) %)))]
    (count (filter rng-cnt-2? range-pairs))))


(comment
  (solve (u/txt-in "day4.txt"))
  (solve sample)
  )
