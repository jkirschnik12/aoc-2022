(ns aoc-2022.utils
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(import 'java.io.PushbackReader)


(defn edn-in
  [resource-file]
  (edn/read (PushbackReader. (io/reader (io/resource resource-file)))))

(defn txt-in
  [file]
  (slurp (io/resource file)))

(defn bin-to-dec
  [bin-str]
  (let [msb (dec (count bin-str))]
    (loop [i 0
           val 0]
      (if-not (> i msb)
        (if-not (zero? (edn/read-string (subs bin-str i (inc i))))
          (recur (inc i) (+ (Math/pow 2 (- msb i)) val))
          (recur (inc i) val))
          val))))
