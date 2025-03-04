package org.example.model;

public interface CellStateMap {
  enum CellState {
    HIDE,
    SHOW,
    FLAG
  }

  /** Resets the cellStateMap using the current dimensions of the map. */
  void resetCellStateMap();

  /** Resets the cellStateMap using the provided dimensions. */
  void newCellStateMap(int height, int width);

  /** Getter method of the CellState value at the cell provided by coordinate. */
  CellState getCellState(Coordinate c);

  /** Setter method of the CellState value at the cell provided by coordinate. */
  void setCellState(Coordinate c, CellState cellState);

  /** Returns true if cell at provided coordinate is HIDE. */
  boolean isHide(Coordinate c);

  /** Returns true if cell at provided coordinate is SHOW. */
  boolean isShow(Coordinate c);

  /** Returns true if cell at provided coordinate is HIDE. */
  boolean isFlag(Coordinate c);

  /** Getter method for cellStateMap's height. */
  int getMapHeight();

  /** Getter method for cellStateMap's width. */
  int getMapWidth();
}
