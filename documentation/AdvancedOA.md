# Advanced Opponent Algorithm
In all difficulties the algorithm will only attempt to place in empty spaces,
to lower computational time in endgames.
AO's immediate wins will also always be scored.
Your immediate wins will also always be blocked.

## Algorithms

| Difficulty |     Method Name     |                              Description                           |
|:----------:|:-------------------:|:------------------------------------------------------------------:|
|     0      |  makeRandomMove()   |                  Moves are made completely randomly.               |
|     1      | makeDefensiveMove() |            Focuses on blocking you, only taking wins-in-1.         |
|     2      | makeStrategicMove() | Depth 3 minimax - computes all possible moves and chooses the best |
|     3      |   makeCruelMove()   |             Tries to block all possible winning lines.             | 
