package org.example.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    if (board == null) {
      throw new IllegalArgumentException("Board cannot be null.");
    }
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  private void checkIndexInBounds(int r, int c) {
    if (r < 0 || r >= this.getHeight() || c < 0 || c >= this.getWidth()) {
      throw new IndexOutOfBoundsException("Index is out of bounds for puzzle.");
    }
  }

  @Override
  public CellType getCellType(Coordinate c) {
    this.checkIndexInBounds(c.row(), c.col());
    int cellValue = board[c.row()][c.col()];
    if (cellValue == 0) {
      return CellType.BLANK;
    } else if (cellValue == 9) {
      return CellType.MINE;
    } else if (1 <= cellValue && cellValue <= 8) {
      return CellType.CLUE;
    } else {
      return CellType.ERROR;
    }
  }

  @Override
  public boolean isBlank(Coordinate c) {
    return getCellType(c) == CellType.BLANK;
  }

  @Override
  public boolean isClue(Coordinate c) {
    return getCellType(c) == CellType.CLUE;
  }

  @Override
  public boolean isMine(Coordinate c) {
    return getCellType(c) == CellType.MINE;
  }

  @Override
  public int getClue(Coordinate c) {
    this.checkIndexInBounds(c.row(), c.col());
    if (this.getCellType(c) != CellType.CLUE) {
      throw new IllegalArgumentException("CellType is not CLUE");
    }
    return board[c.row()][c.col()];
  }
}
