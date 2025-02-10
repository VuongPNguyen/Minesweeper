package org.example.model;

public interface Model {
  /**
   * Reveals the cell if not revealed yet in the active puzzle in the cell at row r, column c.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  void revealCell(int r, int c, boolean rootCell);

  /** Reveals all adjacent cells if the initial cell is a BLANK. */
  void revealBlankAlgorithm(int r, int c);

  /**
   * Adds a flag if one doesn't already exist to the active puzzle in the cell at row r, column c.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  void addFlag(int r, int c);

  /**
   * Removes a flag if one exists from the active puzzle at the cell at row r, column c. Throws an
   * IndexOutOfBoundsException if r or c is out of bounds.
   */
  void removeFlag(int r, int c);

  /**
   * Returns true only if, in the active puzzle, the cell location row r, column c is currently
   * revealed. If the cell itself contains a flag, this method should return false. Throws an
   * IndexOutOfBoundsException if r or c is out of bounds.
   */
  boolean isRevealed(int r, int c);

  /**
   * Returns true only if, in the active puzzle, the cell at row r, column c contains a user-placed
   * flag. Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  boolean isFlag(int r, int c);

  /**
   * Returns true only if, in the active puzzle, the cell at row r, column c contains a mine. Throws
   * an IndexOutOfBoundsException if r or c is out of bounds.
   */
  boolean isMine(int r, int c);

  /**
   * Getter method for the state of the cell at row r, column c. Throws an IndexOutOfBounds
   * exception if r or c is out of bounds.
   */
  CellState getCellState(int r, int c);

  /** Getter method for the current active Puzzle instance */
  Puzzle getActivePuzzle();

  /** Getter method for the active puzzle index */
  int getActivePuzzleIndex();

  /**
   * Setter method for the current active Puzzle index. If the passed index is out of bounds, this
   * method should throw an IndexOutOfBoundsException
   */
  void setActivePuzzleIndex(int index);

  /** Getter method for the number of puzzles contained in the internal PuzzleLibrary */
  int getPuzzleLibrarySize();

  /** Resets the active puzzle by removing all lamps which have been placed */
  void resetPuzzle(RenderType renderType);

  /** Getter method for number of cells required to reveal to win. */
  int getRevealTarget();

  /** Returns true if the active puzzle is solved (i.e. every non-mine cell is revealed) */
  void updateGameState();

  /** Getter method for gameState. */
  GameState getGameState();

  /** Setter method for gameState. */
  void setGameState(GameState gameState);

  /** Adds an observer to the model */
  void addObserver(ModelObserver observer);

  /** Removes an observer from the model */
  void removeObserver(ModelObserver observer);

  /** Getter method for coordinates of tripped mines. */
  int[] getExplodedMine();

  /** Setter method for coordinates of tripped mines. */
  void setExplodedMine(int[] coordinates);
}
