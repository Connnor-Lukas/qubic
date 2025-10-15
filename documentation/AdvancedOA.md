# Advanced Opponent Algorithm
In all difficulties the algorithm will only attempt to place in empty spaces,
to lower computational time in endgames.
AO's immediate wins will also always be scored.
Your immediate wins will also always be blocked.

## Algorithm
These algorithms are a baseline and not all are expected in the final design.

| Difficulty |     Method Name     |                        Description                        |
|:----------:|:-------------------:|:---------------------------------------------------------:|
|     0      |  makeRandomMove()   |            Moves are made completely randomly.            |
|     1      | makeStraightMove()  |          Only goes for straights, no diagonals.           |
|     2      | makeOffensiveMove() |     Attempts to score in any way direction possible.      |
|     3      | makeDefensiveMove() | Focuses fully on blocking you, does not attempt to score. |
|     4      | makeStrategicMove() |          Score based algorithm, pretty complex.           |
