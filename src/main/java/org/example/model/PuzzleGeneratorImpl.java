package org.example.model;

import java.util.Random;

public class PuzzleGeneratorImpl implements PuzzleGenerator {
  private Coordinate safeCell;
  private int[][] board;
  private PuzzleDifficulty puzzleDifficulty;
  private int boardHeight;
  private int boardWidth;
  private int mineCount;

  public PuzzleGeneratorImpl(Coordinate safeCell) {
    this(safeCell, PuzzleDifficulty.EASY);
  }

  public PuzzleGeneratorImpl(Coordinate safeCell, PuzzleDifficulty puzzleDifficulty) {
    this.safeCell = safeCell;
    this.setPuzzleDifficulty(puzzleDifficulty);
  }
  
  @Override
  public Puzzle generateRandomPuzzle(CoordinateImpl coordinate) {
    setSafeCell(coordinate);
    generateBlankBoard();
    placeMines();
    placeClues();
    return new PuzzleImpl(board);
  }
  
  @Override
  public Puzzle generateRandomPuzzle(PuzzleDifficulty puzzleDifficulty) {
    setPuzzleDifficulty(puzzleDifficulty);
    generateBlankBoard();
    placeMines();
    placeClues();
    return new PuzzleImpl(board);
  }

  @Override
  public Puzzle generateRandomPuzzle(PuzzleDifficulty puzzleDifficulty, Coordinate safeCell) {
    setPuzzleDifficulty(puzzleDifficulty);
    setSafeCell(safeCell);
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
    for (int currentMines = 0; currentMines < getMineCount(); currentMines++) {
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
  public Coordinate getSafeCell() {
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
      case EASY -> setPuzzleParameters(8, 10, 10);
      case MEDIUM -> setPuzzleParameters(14, 18, 40);
      case HARD -> setPuzzleParameters(20, 24, 99);
      case CUSTOM -> setPuzzleParameters(10, 10, 12);
    }
  }

  @Override
  public void setSafeCell(Coordinate coordinate) {
    safeCell = coordinate;
  }

  @Override
  public boolean checkSafeCellAdjacency(int row, int col) {
    for (int r = getSafeCell().row() - 1; r <= getSafeCell().row() + 1; r++) {
      for (int c = getSafeCell().col() - 1; c <= getSafeCell().col() + 1; c++) {
        if (r >= 0 && r < board.length && c >= 0 && c < board[0].length) {
          if (r == row && c == col) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public void setPuzzleParameters(int height, int width, int mineCount) {
    // Check negative inputs
    this.boardHeight = Math.max(height, 1);
    this.boardWidth = Math.max(width, 1);
    this.mineCount = Math.max(mineCount, 0);
    // Check mines is viable.
    int numCells = boardHeight * boardWidth;
    if (this.mineCount >= numCells) {
      this.mineCount = numCells - 9;
    }
  }
  
  @Override
  public int getHeight() {
    return boardHeight;
  }
  
  @Override
  public int getWidth() {
    return boardWidth;
  }
  
  @Override
  public int getMineCount() {
    return mineCount;
  }
}
