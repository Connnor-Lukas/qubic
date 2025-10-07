# Key Features
## High Priority Features
### Play Screen
- Demo: When running the file, an intro screen appears with a 1-Player and 2-Player buttons.
- Notes: Add an intro/welcome screen with 2 Play-button.

### Input
- Demo: Using the mouse both players can click on a square marking it as theirs. Invalid (positions) clicks are voided.
- Notes: Display four 4x4 2D boards indicating which corresponds to which layer (z=0,1,2,3) in the 3D view. 

### Turn System
- Demo: When playing the game 2 of the same mark cannot be placed consecutively.
- Notes: Switch marks between each input. 

### Row Checking - Win Detection
- Demo: If a player has a "row", the win screen will appear congratulating them on their win.
- Notes: Check after every input all rows that could have been completed by this move

## Medium Priority Features
### Win Screen
- Demo: A Win screen appears after a player won
- Notes: Create a screen that congratulates the winning player on their success

### 3D View
- Demo: A dynamic 3D Render of the Qubic appears below the input boards and is updated in real-time.
- Notes: Implement a 3D render library to display a 3D-array 4x4x4 array.

### 3D View - Camera Controls
- Demo: In the 3D view, the user can pan and rotate the camera to inspect different angles.
- Notes: Add mouse controls for rotating, zoom, and panning.

### Visual Feedback - Selection & Invalid Moves
- Demo: Hovering over an input square highlights it on the input board and 3D render. Shows different color when the selection is invalid. 
- Improves UX and usability. 

### Artificial Player
- Demo: When user selects the 1-Player mode, all inputs for Player-2 are done automatically by the computer. 
- Notes: Implement a simple program to make semi-calculated moves in response to Player-1 (the human).

## Low Priority Features
### Restart Function
- Demo: A restart button appears on the Win screen that when presses resets the game allowing players to rematch. 
- Notes: Add a restart button on the Win screen to allow the players to rematch

### Win-Line Highlight
- Demo: The winning line is highlighted upon winning.
- Notes: Matching color for the player. 

## Very-Low Priority Features
## Move History
- Demo: a move-history timeline is shown at the bottom of the window. 
- Notes: Show moves done by both players.

# Learning Objectives
## Git & GitHub
- The project is (privately) hosted on GitHub and version-controlled using git. -- add branching, popping (multiple commands)
- Frequent commits are made by both developers to ensure consistency & constant functionality. 

## Maven Build System
- The project will be built using Maven, the structure is defined by our [pom.xml](pom.xml) file.
- Our individual ide's and github itself have been configured to use Maven and autobuild on each commit.

## 3D View
- Find and use an appropriate library to display the 3D view of the Qubic.
- Implement the library & code into the existing Swing infrastructure.

## Basic Game AI for Qubic
- Research AI & Game Heuristics 
- Practical implementation of learned concepts
