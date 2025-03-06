package org.example.model;

import org.example.model.CellStateMap.CellState;
import org.example.model.PuzzleGenerator.PuzzleDifficulty;

public interface Model {
  /**
   * Reveals the cell if not revealed yet in the active puzzle in the cell at row r, column c.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  void revealCell(Coordinate c, boolean rootCell);

  /** Reveals all adjacent cells if the initial cell is a BLANK. */
  void revealBlankAlgorithm(Coordinate c);

  /** Reveals all mines in the puzzle. */
  void revealAllMines();

  /** Reveals all adjacent cells if flags satisfy the clues. */
  void revealAdjacentCells(Coordinate c);

  /**
   * Adds a flag if one doesn't already exist to the active puzzle in the cell at row r, column c.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  void addFlag(Coordinate c);

  /**
   * Removes a flag if one exists from the active puzzle at the cell at row r, column c. Throws an
   * IndexOutOfBoundsException if r or c is out of bounds.
   */
  void removeFlag(Coordinate c);

  /** Creates a new puzzle based on the current difficulty. */
  void newPuzzle(Coordinate c);

  /** Creates a new puzzle based on the difficulty parameter. */
  void newPuzzle(PuzzleDifficulty puzzleDifficulty);

  /** Creates a new puzzle based on the current difficulty. */
  void newPuzzle();

  /** Setter method for puzzleDifficulty */
  void setPuzzleDifficulty(PuzzleDifficulty puzzleDifficulty);

  /** Resets the active puzzle by removing all lamps which have been placed */
  void resetPuzzle();

  /** Returns true if the active puzzle is solved (i.e. every non-mine cell is revealed) */
  void updateGameState();

  /** Adds an observer to the model */
  void addObserver(ModelObserver observer);

  /** Removes an observer from the model */
  void removeObserver(ModelObserver observer);

  // Model Getters and Setters

  /** Getter method for puzzleDifficulty */
  PuzzleDifficulty getPuzzleDifficulty();

  /** Getter method for gameState. */
  GameState getGameState();

  /** Setter method for gameState. */
  void setGameState(GameState gameState);

  /** Getter method for coordinates of tripped mines. */
  Coordinate getExplodedMine();

  /** Setter method for coordinates of tripped mine using row and col values. */
  void setExplodedMine(Coordinate c);

  /** Getter method for number of cells required to reveal to win. */
  int getRevealGoal();

  // Puzzle Generator Methods

  /** Setter method for puzzle parameters. */
  void setPuzzleParameters(int height, int width, int mineCount);

  // Puzzle Methods
  /** Getter method for the current active Puzzle instance */
  Puzzle getPuzzle();

  /** Getter method for puzzle height. */
  int getPuzzleHeight();

  /** Getter method for puzzle width. */
  int getPuzzleWidth();

  /** Getter method for the mineCount of the current puzzle. */
  int getMineCount();

  /**
   * Returns true only if, in the active puzzle, the cell at Coordinate c is BLANK.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  boolean isBlank(Coordinate c);

  /**
   * Returns true only if, in the active puzzle, the cell at Coordinate c is CLUE.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  boolean isClue(Coordinate c);

  /**
   * Returns true only if, in the active puzzle, the cell at Coordinate c is MINE.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds.
   */
  boolean isMine(Coordinate c);

  /**
   * Returns clue value at the cell at provided coordinate.
   * Throws an IllegalArgumentException if cell is not CLUE.
   */
  int getClue(Coordinate c);

  // CellStateMap Methods

  /** Getter method for the cellState at the coordinate parameter. */
  CellState getCellState(Coordinate c);

  /** Returns true if the cell in CellStateMap is HIDE. */
  boolean isHide(Coordinate c);

  /** Returns true if the cell in CellStateMap is FLAG. */
  boolean isFlag(Coordinate c);

  /** Returns true if the cell in CellStateMap is SHOW. */
  boolean isShow(Coordinate c);
}
