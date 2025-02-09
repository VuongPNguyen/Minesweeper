package org.example.model;

public class PuzzleImpl implements Puzzle {
  private int[][] board;
  
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
  public CellType getCellType(int r, int c) {
    this.checkIndexInBounds(r, c);
    int cellValue = board[r][c];
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
  public int getClue(int r, int c) {
    this.checkIndexInBounds(r, c);
    if (this.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("CellType is not CLUE");
    }
    return board[r][c];
  }
}
