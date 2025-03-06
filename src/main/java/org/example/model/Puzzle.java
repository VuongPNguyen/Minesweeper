package org.example.model;

public interface Puzzle {
  enum CellType {
    BLANK,
    CLUE,
    MINE,
    ERROR,
  }

  /** Getter method for the width of the puzzle (i.e. the number of columns it has) */
  int getWidth();

  /** Getter method for the height of the puzzle (i.e. the number of rows it has) */
  int getHeight();

  /**
   * Getter method for the type of the cell located at row r, column c. Throws an IndexOutOfBounds
   * exception if r or c is out of bounds
   */
  CellType getCellType(Coordinate c);

  /** Returns true if cell is CellType BLANK. */
  boolean isBlank(Coordinate c);

  /** Returns true if cell is CellType CLUE. */
  boolean isClue(Coordinate c);

  /** Returns true if cell is CellType MINE */
  boolean isMine(Coordinate c);

  /**
   * Getter method for the clue number of the cell located at row r, column c. Throws an
   * IndexOutOfBounds exception if r or c is out of bounds, or an IllegalArgumentException if the
   * cell is not type CLUE
   */
  int getClue(Coordinate c);
}
