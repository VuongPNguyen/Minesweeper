package org.example.model;

public interface PuzzleGenerator {
  enum PuzzleDifficulty {
    EASY,
    MEDIUM,
    HARD
  }
  /**
   * Generates a new Puzzle object based on the difficulty parameter.
   * Should generate such that the user is guaranteed safety on the first click.
   */
  Puzzle generateRandomPuzzle(PuzzleDifficulty puzzleDifficulty);
  
  /**
   * Generates a new Puzzle object based on the difficulty parameter.
   * Should generate a safe cell at the cell provided by row and col.
   */
  Puzzle generateRandomPuzzle(PuzzleDifficulty puzzleDifficulty, int row, int col);

  /** Creates and assigns a new board to the instance variable to be modified. */
  void generateBlankBoard();

  /** Generates mines on the puzzle board. Ensures that the mines are not adjacent to the safe zone. */
  void placeMines();

  /** Generates clues based on the current mines on the board. */
  void placeClues();

  /** Returns the number of mines adjacent to the given cell. */
  int countAdjacentMines(int row, int col);

  /** Getter method for the coordinates of the safe cell. */
  int[] getSafeCell();

  /** Getter method for the board. */
  int[][] getBoard();

  /** Getter method for the puzzle generator's set difficulty. */
  PuzzleDifficulty getPuzzleDifficulty();

  /** Setter method for the puzzle generator's set difficulty. */
  void setPuzzleDifficulty(PuzzleDifficulty puzzleDifficulty);

  /** Setter method for the puzzle's safe cell. */
  void setSafeCell(int row, int col);

  /** Returns true if current cell is adjacent to safe cell. */
  boolean checkSafeCellAdjacency(int row, int col);
}
