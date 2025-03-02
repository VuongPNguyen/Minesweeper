package org.example.model;

import org.example.model.PuzzleGenerator.PuzzleDifficulty;

public interface Model {
  /**
   * Reveals the cell if not revealed yet in the active puzzle in the cell at row r, column c.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  void revealCell(int r, int c, boolean rootCell);

  /** Reveals all adjacent cells if the initial cell is a BLANK. */
  void revealBlankAlgorithm(int r, int c);
  
  /** Reveals all mines in the puzzle. */
  void revealAllMines();
  
  /** Reveals all adjacent cells if flags satisfy the clues. */
  void revealAdjacentCells(int r, int c);
  
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

  /** Creates a new puzzle based on the current difficulty. */
  void newPuzzle(int row, int col);

  /** Creates a new puzzle based on the current difficulty. */
  void newPuzzle();

  /** Getter method for puzzleDifficulty */
  PuzzleDifficulty getPuzzleDifficulty();

  /** Setter method for puzzleDifficulty */
  void setPuzzleDifficulty(PuzzleDifficulty puzzleDifficulty);
  
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
   * Returns true only if, in the active puzzle, the cell at row r, column c contains a clue. Throws
   * an IndexOutOfBoundsException if r or c is out of bounds.
   */
  boolean isClue(int r, int c);

  /**
   * Getter method for the state of the cell at row r, column c. Throws an IndexOutOfBounds
   * exception if r or c is out of bounds.
   */
  CellState getCellState(int r, int c);

  /** Getter method for the current active Puzzle instance */
  Puzzle getActivePuzzle();

  /** Resets the active puzzle by removing all lamps which have been placed */
  void resetPuzzle(RenderType renderType);

  /** Getter method for number of cells required to reveal to win. */
  int getRevealGoal();

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
