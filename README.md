# Qubic (3D Connect-Four)

## Overview

Qubic is a 4x4x4 Tic-Tac-Toe game made in Java, primarily using Swing UI and
JavaFX. It supports both two-player and single-player modes. Finished games are
automatically saved and can be replayed. The interface includes a 3D render of
the cube with and input board, move preview, and a move history on the side.

## Installation

Requires Java 17+

### Build & Run

The project is runnable through your ide (intellij and vscode assuming java
extensions are installed). It is also runnable through a system install of maven
using this below:

```bash
mvn clean compile javafx:run
```

### Things to Note

If encountering issues with Replays and Winning, especially on Windows, please
ensure that folder Qubic under /AppData/Roaming exists. In this case, please
create it manually or ensure you are executing the program with sufficient
permissions.

Due to a limitation in JavaFX using directx on Windows the rendering may look
funny, this is an unfortunate side effect that cannot be fixed without rewriting
the entire 3D backend. For best experience please use MacOS or Linux.

## How to Play

### Start a Game

1. Launch the program
2. Select either:

- `1-Player Game`
  - Select one the four difficulties to play against: Random Placement,
    Defensive OA (Opponent Algorithm), Strategic OA, and Cruel OA
- `2-Player Game`
  - Local hot-seat play for 2 persons

3. Four 4x4 boards are seen on the left, a Render of the these grids in the
   center, and the move history on the right. Plays alternate placing their
   respective marks on the grid.
4. As you play the input grid, render and move history slowly fill up with
   moves.

### Goal

- Both players try to get 4 of their marks in a straight line. These can be:
  - Rows, columns, verticals
  - Face diagonals
  - Space diagonals

- Controls
  - Hover over a cell on the input board on the left to preview your move on the
    render.
  - Click a cell to finalize your move.

## Saves & Replays

### Saves

- Every finished game is automatically saved in a `.json` file under
  - `user.home/AppData/Roaming/Qubic` for Windows
  - `user.home/Library/Application Support/Qubic` for MacOS
  - `user.home/.local/share` for Linux / UNIX

### Replaying

- Clicking `Replays` on the home screen, shows up a menu of all your past games.
- Clicking the blue ▶ on the right shows the finals state of the game
- Clicking ◀ or ▶ on the top undoes or does 1 move, respectively. You may also
  use left and right arrows on your keyboard or A and D, holding the keyboard
  options makes it go quicker as well.
- `Menu` returns you to the main menu.

## Difficulties Explained

All difficulties are based on an abstract class that will always block your
immediate wins and score its own immediate wins, this means even the easiest
mode Random requires _some_ level of strategy. Apart from this they work as
follows:

- **Random**: Chooses moves randomly.
- **Defensive OA**: Focuses on blocking your moves, doing its best to prevent
  you from getting a 3-in-a-line or even 2-in-a-line.
- **Strategic OA**: Calculates the best move 3 moves ahead. A basic Minimax
  implementation.
- **Cruel OA**: Tries to block every line where you can possibly win.

## Used Resources

|                                                                                                                                                                                                      References                                                                                                                                                                                                       |                                             Used For                                             |
| :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: | :----------------------------------------------------------------------------------------------: |
| Wikipedia Contributors. (2019, June 7). Prime number. Wikipedia; Wikimedia Foundation. https://en.wikipedia.org/wiki/Prime_number <br/> AnimationTimer (JavaFX 8). (n.d.). Docs.oracle.com. https://docs.oracle.com/javase/8/javafx/api/javafx/animation/AnimationTimer.html <br/> Rotate (JavaFX 8). (2015, February 10). Oracle.com. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/transform/Rotate.html |              [3D Animation](src/main/java/dev/ccsio/qubic/ui/common/Render3D.java)               |
|                                                                                                             Algorithms Explained – minimax and alpha-beta pruning. https://www.youtube.com/watch?v=l-hh51ncgDI <br/> Minimax: How Computers Play Games. https://www.youtube.com/watch?v=SLgZhpDsrfc <br/>                                                                                                             | [Strategic Opponent Algorithm](src/main/java/dev/ccsio/qubic/game/algorithms/StrategicMove.java) |

## Developers

- Connor Alecks
- Lukáš Hlaváč

## Planning

Our plans have been split into 2 levels, top level and second level. The level
system for the plans is designed so that the next level relies on the previous
level to be completed before implementation is possible. The overall project has
been split into multiple goals so that we can effectively make use of more
advanced git features such as branching and work on features individually.

### Top Level

| ID  |                      Plan                      | Dev to Implement |  Status   |
| :-: | :--------------------------------------------: | :--------------: | :-------: |
| a01 |  [Core Game Logic](documentation/CoreGame.md)  |      Lukas       | Completed |
| a02 | [Basic UI Framework](documentation/BasicUI.md) |      Connor      | Completed |

### Second Level

| ID  |                         Plan                         | Dev to Implement |  Status   |
| :-: | :--------------------------------------------------: | :--------------: | :-------: |
| b01 |      [3D Rendering](documentation/3DRender.md)       |      Connor      | Completed |
| b02 | [Game Interaction](documentation/GameInteraction.md) |      Connor      | Completed |
| b03 |    [Player System](documentation/PlayerSystem.md)    |      Lukas       |  On-Hold  |
| b04 |             [VFX](documentation/VFX.md)              |       und        |   TODO    |
| b05 |        [UI Polish](documentation/UIPolish.md)        |      Connor      | Completed |
| b06 | [Basic Opponent Algorithm](documentation/BasicOA.md) |      Lukas       | Completed |
| b07 |   [Game Master Class](documentation/GameMaster.md)   |      Lukas       | Completed |
| b08 | [Maven Automatic Tests](documentation/MavenTests.md) |  Connor & Lukas  | Completed |
| b09 |      [Advanced OA](documentation/AdvancedOA.md)      |      Lukas       | Completed |
