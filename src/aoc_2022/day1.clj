(ns aoc-2022.day1
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.java.io :as io]))

(defn load-text-input
  [file]
  (slurp (io/resource file)))

(def sample-in
  "1000
2000
3000

4000

5000
6000

7000
8000
9000

10000")

(defn solve
  [in]

  
  (->> in
       (str/split-lines)
       (map edn/read-string)
       (partition-by nil?)
       (remove #(every? nil? %))
       (map #(apply + %))
       (sort >)
       (take 3)
       (apply +)
       ))

(comment
  
  (solve (load-text-input "day1.txt"))
  )
