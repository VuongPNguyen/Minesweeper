package org.example.model;

public interface PuzzleLibrary {
  /**
   * Adds the given puzzle so that it is the last puzzle in the library. Throws an
   * IllegalArgumentException if null is passed
   */
  void addPuzzle(Puzzle puzzle);

  /**
   * Returns the puzzle at the specified index. Throws an IndexOutOfBoundsException if the specified
   * index is outside the bounds of the puzzle library
   */
  Puzzle getPuzzle(int index);

  /** Returns the size of the puzzle library, which is the number of puzzles it contains */
  int size();
}
