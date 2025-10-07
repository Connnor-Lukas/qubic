# Core Game Logic
## Board Design
### Board Representation
The game board will be its own class `GameBoard` implemented as a 3D integer array with the below values.

| Value |   Meaning   |
|:-----:|:-----------:|
|   0   | Empty space |
|   1   |  Player 1   |
|  -1   |  Player 2   |

### Win Detection
By using integer values, a winner can be determined through the sum of rows, columns, and diagonals.  
If any line (horizontal, vertical, or diagonal) has a sum of `4` or `-4`, Player 1 or Player 2 wins respectively.

### Object-Oriented Design
A dedicated `Coordinate` class will represent board positions.  
This allows:
- Input validation
- Clear access via `coordinate.x`, `coordinate.y`, and `coordinate.z`

## Planned Features
### Win Condition Checking
- Implement logic for:
    - Horizontal wins
    - Vertical wins
    - Diagonal wins (within planes)
    - 3D diagonals (across all dimensions)

### Turn Management
- Create a turn system to alternate between Player 1 and Player 2

### Game State Management
- Define clear game states:
    - Ongoing
    - Win
    - Draw