# Minesweeper
My rendition of Minesweeper using Java and JavaFX.
Download the .jar file here: [Minesweeper](https://github.com/VuongPNguyen/Minesweeper/raw/refs/heads/main/out/artifacts/Minesweeper_jar/Minesweeper.jar)

Patch 3.1.0 (3/5/2025)

- Added quality of life changes:
  - Added control guide in the UI.
  - Added limiters to width and mine count for custom puzzles.
  - Updated puzzle generation for custom puzzles so there can be at least 1 safe cell.
- Refactored code with more classes.

Patch 3.0.0 (3/1/2025)

- Implemented a new puzzle generation algorithm.
  - Completely randomly generated puzzles.
  - The first reveal is guaranteed to be safe. (No more losing on the first click!)
  - Different-sized puzzles for each difficulty but no less random and unlimited.
- Changed flag image to ⚑ symbol to reduce load times with large amounts of flag images.

Patch 2.0.0 (2/28/2025)
- Greatly improved loading times for puzzles, flagging, and the end stage. Added functionality of showing incorrect flag placements.
  - Changed mine image to ⨷ symbol.
  - PlayGrid now renders one layer instead of two separate layers (Puzzle and Play).
  - An additional layer was added to render mines and incorrect flags on loss.
    - Correct flag placements are no longer revealed.
  - Different types of rendering were added to update specific items specifically.

Patch 1.0.0 (2/10/2025)
- Basic Functionality.
- 3 Base Puzzles.
- It is playable.
