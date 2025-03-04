package org.example.model;

public class CellStateMapImpl implements CellStateMap {
  private CellState[][] cellStateMap;

  public CellStateMapImpl(int height, int width) {
    if (height < 1) {
      throw new IllegalArgumentException("Height of CellStateMap is invalid.");
    }
    if (width < 1) {
      throw new IllegalArgumentException("Width of CellStateMap is invalid.");
    }

    cellStateMap = new CellState[height][width];

    for (int h = 0; h < height; h++) {
      for (int w = 0; w < width; w++) {
        setCellState(new CoordinateImpl(h, w), CellState.HIDE);
      }
    }
  }

  @Override
  public void resetCellStateMap() {
    for (int h = 0; h < this.getMapHeight(); h++) {
      for (int w = 0; w < this.getMapWidth(); w++) {
        setCellState(new CoordinateImpl(h, w), CellState.HIDE);
      }
    }
  }

  @Override
  public void newCellStateMap(int height, int width) {
    cellStateMap = new CellState[height][width];
    for (int h = 0; h < this.getMapHeight(); h++) {
      for (int w = 0; w < this.getMapWidth(); w++) {
        setCellState(new CoordinateImpl(h, w), CellState.HIDE);
      }
    }
  }

  @Override
  public CellState getCellState(Coordinate c) {
    return cellStateMap[c.row()][c.col()];
  }

  @Override
  public void setCellState(Coordinate c, CellState s) {
    cellStateMap[c.row()][c.col()] = s;
  }

  @Override
  public boolean isHide(Coordinate c) {
    return getCellState(c) == CellState.HIDE;
  }

  @Override
  public boolean isShow(Coordinate c) {
    return getCellState(c) == CellState.SHOW;
  }

  @Override
  public boolean isFlag(Coordinate c) {
    return getCellState(c) == CellState.FLAG;
  }

  @Override
  public int getMapHeight() {
    return cellStateMap.length;
  }

  @Override
  public int getMapWidth() {
    return cellStateMap[0].length;
  }
}
