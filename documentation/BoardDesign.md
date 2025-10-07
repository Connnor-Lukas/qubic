# Board Design
## Object Type
The board will be a 3d int array using:
- 0 - for blank
- 1 - for player 1
- -1 - for player 2

## Win Detection
The above int system allows us to use a summation of rows and diagonals to determine the winner, if any sum is either 4 or -4 then player 1 or player 2 wins the game.

## Object Orientated Design
The coordinates will be their own class to allow input validification and access using coordinate.x .y and .z.