(ns aoc-2022.day2
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn load-text-input
  [file]
  (slurp (io/resource file)))

(def rps-map
  {"A" :rock
   "X" :rock
   "Y" :paper
   "B" :paper
   "C" :scissors
   "Z" :scissors})

(def score
  {:rock 1
   :paper 2
   :scissors 3})

(def result
  {:win 6
   :loss 0
   :draw 3})

(def sample "A Y
B X
C Z")

(defn eval-game
  [[x y]]
  (let [y-score (get score y)
        r (cond
            (= x y)
            :draw

            (or (and (= x :paper)
                     (= y :scissors))
                (and (= x :rock)
                     (= y :paper))
                (and (= x :scissors)
                     (= y :rock)))
            :win

            :else
            :loss)
        game-res (get result r)]
    (+ y-score game-res)))

(defn solve
  [input]
  (->> input
       (str/split-lines)
       (map #(str/split % #" "))
       (map #(map (fn [sym]
                    (get rps-map sym)) %))
       (map eval-game)
       (apply +)))

(comment
  (load-text-input "day2.txt")
  (solve (load-text-input "day2.txt")))
