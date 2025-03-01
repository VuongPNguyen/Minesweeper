package org.example.model;

import java.util.Random;

public class PuzzleGeneratorImpl implements PuzzleGenerator {
  private int[] safeCell;
  private int[][] board;
  private PuzzleDifficulty puzzleDifficulty;
  private int boardHeight;
  private int boardWidth;
  private int mineCount;

  public PuzzleGeneratorImpl(int[] safeCell) {
    this(safeCell, PuzzleDifficulty.EASY);
  }

  public PuzzleGeneratorImpl(int[] safeCell, PuzzleDifficulty puzzleDifficulty) {
    this.safeCell = safeCell;
    this.setPuzzleDifficulty(puzzleDifficulty);
    generateBlankBoard();
  }

  @Override
  public Puzzle generateRandomPuzzle(PuzzleDifficulty puzzleDifficulty) {
    generateBlankBoard();
    placeMines();
    placeClues();
    return new PuzzleImpl(board);
  }
  
  @Override
  public Puzzle generateRandomPuzzle(PuzzleDifficulty puzzleDifficulty, int row, int col) {
    setSafeCell(row, col);
    generateBlankBoard();
    placeMines();
    placeClues();
    return new PuzzleImpl(board);
  }

  @Override
  public void generateBlankBoard() {
    int[][] tempBoard = new int[boardHeight][boardWidth];

    for (int row = 0; row < boardHeight; row++) {
      for (int col = 0; col < boardWidth; col++) {
        tempBoard[row][col] = 0;
      }
    }

    board = tempBoard;
  }

  @Override
  public void placeMines() {
    Random random = new Random();
    for (int currentMines = 0; currentMines < mineCount; currentMines++) {
      int mineRow = random.nextInt(boardHeight);
      int mineCol = random.nextInt(boardWidth);
      while (board[mineRow][mineCol] == 9 || checkSafeCellAdjacency(mineRow, mineCol)) {
        mineRow = random.nextInt(boardHeight);
        mineCol = random.nextInt(boardWidth);
      }
      board[mineRow][mineCol] = 9;
    }
  }

  @Override
  public void placeClues() {
    for (int row = 0; row < boardHeight; row++) {
      for (int col = 0; col < boardWidth; col++) {
        if (board[row][col] != 9) {
          board[row][col] = countAdjacentMines(row, col);
        }
      }
    }
  }

  @Override
  public int countAdjacentMines(int row, int col) {
    int mines = 0;

    for (int r = row - 1; r <= row + 1; r++) {
      for (int c = col - 1; c <= col + 1; c++) {
        if (r >= 0 && r < boardHeight && c >= 0 && c < boardWidth) {
          if (board[r][c] == 9) {
            mines++;
          }
        }
      }
    }
    return mines;
  }

  @Override
  public int[] getSafeCell() {
    return safeCell;
  }

  @Override
  public int[][] getBoard() {
    return board;
  }

  @Override
  public PuzzleDifficulty getPuzzleDifficulty() {
    return puzzleDifficulty;
  }

  @Override
  public void setPuzzleDifficulty(PuzzleDifficulty puzzleDifficulty) {
    this.puzzleDifficulty = puzzleDifficulty;
    generateBlankBoard();
    switch (puzzleDifficulty) {
      case EASY -> {
        boardHeight = 8;
        boardWidth = 10;
        mineCount = 10;
      }
      case MEDIUM -> {
        boardHeight = 14;
        boardWidth = 18;
        mineCount = 40;
      }
      case HARD -> {
        boardHeight = 20;
        boardWidth = 24;
        mineCount = 99;
      }
    }
  }

  @Override
  public void setSafeCell(int row, int col) {
    safeCell = new int[] {row, col};
  }

  @Override
  public boolean checkSafeCellAdjacency(int row, int col) {
    for (int r = getSafeCell()[0] - 1; r <= getSafeCell()[0] + 1; r++) {
      for (int c = getSafeCell()[1] - 1; c <= getSafeCell()[1] + 1; c++) {
        if (r >= 0 && r < board.length && c >= 0 && c < board[0].length) {
          if (r == row && c == col) {
            return true;
          }
        }
      }
    }
    return false;
  }
}
