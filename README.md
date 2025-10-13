# qubic
Simple Qubic (3-dimensional tic-tac-toe) game in Java. Our initial goals are defined in [the backlog](documentation/BACKLOG.md).

## Planning
Our plans have been split into 2 levels, top level and second level. The level system for the plans is designed so that the next level relies on the previous level to be completed before implementation is possible. The overall project has been split into multiple goals so that we can effectively make use of more advanced git features such as branching and work on features individually.

### Top Level

| ID  |                      Plan                      | Dev to Implement |   Status    |
|:---:|:----------------------------------------------:|:----------------:|:-----------:|
| a01 |  [Core Game Logic](documentation/CoreGame.md)  |      Lukas       |  Completed  |
| a02 | [Basic UI Framework](documentation/BasicUI.md) |      Connor      | In-Progress |

### Second Level

| ID  |                         Plan                         | Dev to Implement |   Status    |
|:---:|:----------------------------------------------------:|:----------------:|:-----------:|
| b01 |      [3D Rendering](documentation/3DRender.md)       |      Connor      | In-Progress |
| b02 | [Game Interaction](documentation/GameInteraction.md) |       und        |    TODO     |
| b03 |    [Player System](documentation/PlayerSystem.md)    |      Lukas       | In-Progress |
| b04 |             [VFX](documentation/VFX.md)              |       und        |    TODO     |
| b05 |        [UI Polish](documentation/UIPolish.md)        |       und        |    TODO     |
| b06 |  [Opponent Algorithm](documentation/OpponentAI.md)   |       und        |    TODO     |
| b07 |   [Game Master Class](documentation/GameMaster.md)   |      Lukas       | In-Progress |

## Used Resources

|                                                                                                                                                                                                       References                                                                                                                                                                                                       |                               Used For                                |
|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------:|
| Wikipedia Contributors. (2019, June 7). Prime number. Wikipedia; Wikimedia Foundation. https://en.wikipedia.org/wiki/Prime_number <br/> AnimationTimer (JavaFX 8). (n.d.). Docs.oracle.com. https://docs.oracle.com/javase/8/javafx/api/javafx/animation/AnimationTimer.html <br/> Rotate (JavaFX 8). (2015, February 10). Oracle.com. https://docs.oracle.com/javase/8/javafx/api/javafx/scene/transform/Rotate.html  | [3D Animation](src/main/java/dev/ccsio/qubic/ui/common/Render3D.java) |

