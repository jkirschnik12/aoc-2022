(ns aoc-2022.day5
  (:require [clojure.string :as str]
            [clojure.edn :as edn]
            [clojure.set :as set]
            [aoc-2022.utils :as u]
            [clojure.java.io :as io]))

(def sample "Z N
M C D
P

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2")

"[X] "

(defn solve
  [input]
  (let [[c _ i] (->> input
                     (str/split-lines)
                     (partition-by #(= "" %))
                     )
        stks (->> c
         (map #(str/split % #" "))
         (map reverse))
        o (->> i
         (map #(str/replace % #"move " ""))
         (map #(str/replace % #"from " ""))
         (map #(str/replace % #"to " ""))
         (map #(str/split % #" "))
         (map #(map (fn [a]
                      (edn/read-string a)) %)))]
    (->> o
         (reduce (fn [acc [amt src dest]]
                   (println acc [amt src dest])
                   (let [move (vec (reverse (vec (take amt (vec (nth acc (dec src)))))))
                         new-src (vec (drop amt (nth acc (dec src))))
                         new-dest (vec (into move (vec (nth acc (dec dest)))))]
                     (-> acc
                         (assoc (dec src) new-src)
                         (assoc (dec dest) new-dest)))) (vec (map vec stks)))
     
     (map first)
     (str/join))))

(defn solve-v2
  [input]
  (let [[c _ i] (->> input
                     (str/split-lines)
                     (partition-by #(= "" %))
                     )
        stks (->> c
         (map #(str/split % #" "))
         (map reverse))
        o (->> i
         (map #(str/replace % #"move " ""))
         (map #(str/replace % #"from " ""))
         (map #(str/replace % #"to " ""))
         (map #(str/split % #" "))
         (map #(map (fn [a]
                      (edn/read-string a)) %)))]
    (->> o
         (reduce (fn [acc [amt src dest]]
                   (println acc [amt src dest])
                   (let [move (vec (take amt (vec (nth acc (dec src)))))
                         new-src (vec (drop amt (nth acc (dec src))))
                         new-dest (vec (into move (vec (nth acc (dec dest)))))]
                     (-> acc
                         (assoc (dec src) new-src)
                         (assoc (dec dest) new-dest)))) (vec (map vec stks)))
     
     (map first)
     (str/join))))

(comment
  (solve (u/txt-in "day5.txt"))
  (solve-v2 (u/txt-in "day5.txt"))
  (solve sample)
  (u/txt-in "day5.txt")
)
