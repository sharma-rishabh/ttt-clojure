(ns ttt)

(def game
  {:current-player {:name :player1 :symbol :X :moves #{1 2}}
   :next-player    {:name :player2 :symbol :O :moves #{2 3}}})

(def winning-moves
  #{#{7 6 8} #{2 5 8} #{0 6 3} #{0 1 2} #{0 4 8} #{4 3 5} #{4 6 2} #{7 1 4}})

(defn subset?
  [set1 set2]
  (every? #(set1 %1) set2))

(defn get-current-player-moves
  [game]
  (:moves (:current-player game)))

(defn get-next-player-moves
  [game]
  (:moves (:next-player game)))

(defn drawn?
  [game]
  (>=
   (+
    (count (get-current-player-moves game))
    (count (get-next-player-moves game)))
   9))

(defn current-player-won?
  [game]
  (some #(subset? (get-current-player-moves game) %) winning-moves))

(defn game-over?
  [game]
  (or (current-player-won? game) (drawn? game)))

(defn play-move
  [game move]
  (assoc-in game [:current-player :moves]
            (conj (get-current-player-moves game) move)))

(defn swap-players
  [{current-player :current-player
    next-player :next-player}]
  {:current-player next-player :next-player current-player})

(def read-move (comp read-string read-line))

(defn show-winner [current-game]
  (if (current-player-won? current-game)
    (println (:name (:current-player current-game)) " WON!!")
    (println "DRAWN!!")))

(defn play-game
  []
  (loop
   [game game]
    (let
     [current-game (->> (read-move)
                        (play-move game))]
      (prn current-game)
      (if (game-over? current-game)
        (show-winner current-game)
        (recur (swap-players current-game))))))

(play-game)