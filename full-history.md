```mermaid
gitGraph TB:
    commit
    branch MavenSetup
    commit
    checkout main
    commit
    merge MavenSetup
    commit
    branch Render3D
    commit
    checkout main
    branch CoreGame
    commit
    checkout Render3D
    branch BasicUI
    commit
    checkout CoreGame
    commit
    checkout BasicUI
    commit
    checkout main
    commit
    checkout BasicUI
    commit
    checkout CoreGame
    commit
    checkout main
    merge CoreGame
    commit
    checkout Render3D
    commit
    checkout main
    commit
    branch GameMaster
    commit
    checkout BasicUI
    merge main
    checkout Render3D
    merge main
    checkout main
    commit
    checkout Render3D
    commit
    checkout GameMaster
    commit
    checkout main
    commit
    checkout GameMaster
    commit
    checkout BasicUI
    merge GameMaster
    checkout GameMaster
    commit
    checkout BasicUI
    merge GameMaster
    checkout GameMaster
    commit
    checkout BasicUI
    commit
    checkout BasicUI
    merge GameMaster
    checkout main
    merge GameMaster
    branch AdvancedOA
    commit
    checkout BasicUI
    commit
    checkout main
    merge BasicUI
    checkout AdvancedOA
    commit
    checkout Render3D
    merge main
    checkout AdvancedOA
    merge main
    commit
    checkout Render3D
    commit
    checkout AdvancedOA
    commit
    checkout Render3D
    commit
    checkout main
    merge Render3D
    commit
    branch MoveHistory
    commit
    checkout main
    commit
    checkout AdvancedOA
    commit
    checkout main
    branch DifficultyMenu
    commit
    checkout AdvancedOA
    commit
    checkout Render3D
    merge DifficultyMenu
    checkout AdvancedOA
    commit
    checkout Render3D
    commit
    checkout main
    merge Render3D
    commit
    checkout AdvancedOA
    merge main
    commit
    checkout main
    merge AdvancedOA
    commit
    branch GameHistory
    commit
    checkout main
    commit
    branch OAFixes
    commit
    checkout GameHistory
    commit
    merge OAFixes
    checkout OAFixes
    commit
    checkout main
    merge OAFixes
    commit
    branch OAvOA
    commit
    checkout GameHistory
    commit
    branch MergeConflict
    commit
    checkout GameHistory
    merge MergeConflict
    merge main
    commit
    checkout main
    merge GameHistory
```