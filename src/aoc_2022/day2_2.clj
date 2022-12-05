(ns aoc-2022.day2-2
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn load-text-input
  [file]
  (slurp (io/resource file)))

(def rps-map
  {"A" :rock
   "B" :paper
   "C" :scissors
   "X" :loss
   "Y" :draw
   "Z" :win})

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

(def win-map
  {:rock :paper
   :scissors :rock
   :paper :scissors})

(def loss-map
  {:paper :rock 
   :rock :scissors 
    :scissors :paper})

(defn eval-game
  [[x y]]
  (let [choice
        (cond
          (= y :draw)
          x

          (= y :win)
          (get win-map x)

          :else
          (get loss-map x)
          
          )
        s (get score choice)
        ]
    (println y)
    (+ s (get result y))))

(defn solve
  [input]
  (->> input
       (str/split-lines)
       (map #(str/split % #" "))
       (map #(map (fn [sym]
                    (get rps-map sym)) %))
  (map eval-game)
  (apply + )

))

(comment

  (solve   (load-text-input "day2.txt"))
  )
