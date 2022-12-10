(ns aoc-2022.day6
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.set :as set]
            [medley.core :as medley]
            [aoc-2022.utils :as u]
            [clojure.java.io :as io]))

(def sample "mjqjpqmgbljsphdztnvjfqwrcgsmlb")

(def p1 4)
(def p2 14)

(defn solve
  [input p]
  (let [a (->> input
               (partition p 1)
               (medley/find-first #(= p (count (distinct %)))))
        b (str/join a)]
    (+ p (str/index-of input b))))

(comment
  (solve (u/txt-in "day6.txt") p2)

  (solve sample p1)
  (solve "bvwbjplbgvbhsrlpgdmjqwftvncz" p1)
  (solve "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" p1))
