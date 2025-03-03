package org.example.model;

public interface PuzzleGenerator {
  
  enum PuzzleDifficulty {
    EASY,
    MEDIUM,
    HARD,
    CUSTOM
  }

  /**
   * Generates a new Puzzle object based on the current difficulty parameter.
   * Should generate such that the user is guaranteed safety on the first click.
   */
  Puzzle generateRandomPuzzle(CoordinateImpl coordinate);

  /**
   * Generates a new Puzzle object based on the difficulty parameter.
   * Should generate such that the user is guaranteed safety on the first click.
   */
  Puzzle generateRandomPuzzle(PuzzleDifficulty puzzleDifficulty);
  
  /**
   * Generates a new Puzzle object based on the difficulty parameter.
   * Should generate a safe cell at the cell provided coordinate.
   */
  Puzzle generateRandomPuzzle(PuzzleDifficulty puzzleDifficulty, Coordinate safeCell);
  
  /** Creates and assigns a new board to the instance variable to be modified. */
  void generateBlankBoard();

  /** Generates mines on the puzzle board. Ensures that the mines are not adjacent to the safe zone. */
  void placeMines();

  /** Generates clues based on the current mines on the board. */
  void placeClues();

  /** Returns the number of mines adjacent to the given cell. */
  int countAdjacentMines(int row, int col);

  /** Getter method for the coordinates of the safe cell. */
  Coordinate getSafeCell();

  /** Getter method for the board. */
  int[][] getBoard();

  /** Getter method for the puzzle generator's set difficulty. */
  PuzzleDifficulty getPuzzleDifficulty();

  /** Setter method for the puzzle generator's set difficulty. */
  void setPuzzleDifficulty(PuzzleDifficulty puzzleDifficulty);

  /** Setter method for the puzzle's safe cell. */
  void setSafeCell(Coordinate safeCell);

  /** Returns true if current cell is adjacent to safe cell. */
  boolean checkSafeCellAdjacency(int row, int col);
  
  /** Setter method for puzzleHeight, puzzleWidth, and mineCount parameters. */
  void setPuzzleParameters(int height, int width, int mineCount);
  
  /** Getter method for the puzzle's height */
  int getHeight();
  
  /** Getter method for the puzzle's width */
  int getWidth();
  
  /** Getter method for mineCount */
  int getMineCount();
}
