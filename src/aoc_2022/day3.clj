(ns aoc-2022.day3
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.set :as set]
            [clojure.java.io :as io]))

(def sample "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw")

(def m
  "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")


(defn get-p
  [l]
  (inc (str/index-of m l)))



(defn load-text-input
  [file]
  (slurp (io/resource file)))

(defn solve
  [input]
  (let [i (->> input
             (str/split-lines)
             (map #(partition (quot (count %) 2) %))
             (map (fn [[a b]]
                    (set/intersection (set a) (set b))))
             (map (comp get-p str first))
             (apply +))]
    i)
  )

(comment
  (let [in (load-text-input "day3.txt")]
     (solve in)))
