(ns aoc-2022.day7
  "oofta what a mess"
  (:require
   [aoc-2022.utils :as u]
   [clojure.edn :as edn]
   [clojure.string :as str]
   [medley.core :as medley]))

(def sample "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(def u 100000)

(def max-size 70000000)

(def space-needed 30000000)

(defn calc-dir-size
  [tree data path]
  (reduce-kv (fn [acc k v]
               (if (number? v)
                 (+ acc v)
                 (+ acc (let [sub-dir (conj path k)]
                          (calc-dir-size tree
                                         (second (medley/find-first (fn [[cmd data]]
                                                                      (= cmd sub-dir)) tree))
                                         sub-dir))))) 0 data))

(defn traverse
  [tree]
  (loop [current (first tree)
         rem (rest tree)
         acc {}]
    (if current
      (let [[n-path data] current
            out (calc-dir-size tree data n-path)]
        (recur (first rem) (rest rem) (assoc acc n-path out)))
      acc)))

(defn build-path
  [all-cmds]
  (loop [path []
         out []
         cmds (first all-cmds)
         rem (rest all-cmds)]
    (if cmds
      (let [c-path (reduce (fn [acc v]
                             (if (= v "..")
                               (vec (butlast acc))
                               (conj acc v))) path cmds)]
        (recur c-path (conj out c-path) (first rem) (rest rem)))
      out)))

(defn format-input
  [in]
  (let [j (->> in
               (str/split-lines)
               (partition-by #(str/starts-with? % "$"))
               (partition 2)
               (mapv vec))]
    (mapv vector
          (->> (mapv first j)
               (mapv (fn [cmds]
                       (mapv #(subs % 5) (butlast cmds))))
               build-path)
          (reduce (fn [acc [_cmd res]]
                    (conj acc
                          (apply hash-map
                                 (mapcat (fn [itm]
                                           (let [[a b] (->> (str/split itm #" ")
                                                            reverse)]
                                             (if (= b "dir")
                                               [a :dir]
                                               [a (edn/read-string b)]))) res)))) [] j))))

(defn solve
  [in]
  (let [a (format-input in)]
    (->> (traverse a)
         vals
         (filter #(< % u))
         (apply +))))

(defn solve-v2
  [in]
  (let [a (format-input in)
        file (traverse a)
        file-system-size (get file ["/"])
        size-rem (- max-size file-system-size)
        size-needed (- space-needed size-rem)]
    (->> (vals file)
         (filter #(> % size-needed))
         (apply min))))

(comment
  (keys (solve (u/txt-in "day7.txt")))
  (format-input sample)
  (solve-v2 sample)
  (solve-v2 (u/txt-in "day7.txt")) 1815525)
