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
  public Puzzle generateRandomPuzzle(Coordinate coordinate) {
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
    // Generate normally when mines can be easily placed outside safe zone.
    if (getMineCount() < boardHeight * boardWidth - 8) {
      for (int currentMines = 0; currentMines < getMineCount(); currentMines++) {
        int mineRow = random.nextInt(boardHeight);
        int mineCol = random.nextInt(boardWidth);
        while (board[mineRow][mineCol] == 9 || checkSafeCellAdjacency(mineRow, mineCol)) {
          mineRow = random.nextInt(boardHeight);
          mineCol = random.nextInt(boardWidth);
        }
        board[mineRow][mineCol] = 9;
      }
    } else {
      // Place mines in all non-adjacent cells.
      for (int row = 0; row < boardHeight; row++) {
        for (int col = 0; col < boardWidth; col++) {
          if (!checkSafeCellAdjacency(row, col)) {
            board[row][col] = 9;
          }
        }
      }

      // Count adjacent cells.
      int adjacentCells = 0;
      for (int r = getSafeCell().row() - 1; r <= getSafeCell().row() + 1; r++) {
        for (int c = getSafeCell().col() - 1; c <= getSafeCell().col() + 1; c++) {
          if (isInBounds(r, c)) {
            if (r != getSafeCell().row() || c != getSafeCell().col()) {
              adjacentCells++;
            }
          }
        }
      }

      // Randomly select adjacent cell for mine placement.
      int tempMineCount = boardHeight * boardWidth - adjacentCells;
      tempMineCount = getMineCount() + 1 - tempMineCount;
      for (int currentMines = 0; currentMines < tempMineCount; currentMines++) {
        int mineRow = random.nextInt(safeCell.row() - 1, safeCell.row() + 2);
        int mineCol = random.nextInt(safeCell.col() - 1, safeCell.col() + 2);

        while (!isInBounds(mineRow, mineCol)
            || board[mineRow][mineCol] == 9
            || (mineRow == safeCell.row() && mineCol == safeCell.col())) {
          mineRow = random.nextInt(safeCell.row() - 1, safeCell.row() + 2);
          mineCol = random.nextInt(safeCell.col() - 1, safeCell.col() + 2);
        }
        board[mineRow][mineCol] = 9;
      }
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
        if (isInBounds(r, c)) {
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
      case CUSTOM -> setPuzzleParameters(10, 10, 20);
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
        if (isInBounds(r, c)) {
          if (r == row && c == col) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public boolean isInBounds(int row, int col) {
    return row >= 0 && row < getHeight() && col >= 0 && col < getWidth();
  }

  @Override
  public void setPuzzleParameters(int height, int width, int mineCount) {
    // Check negative inputs
    this.boardHeight = Math.max(height, 1);
    this.mineCount = Math.max(mineCount, 0);

    // Keep maximum viable puzzle ratio
    double maxPuzzleWidth = height * 1.75;
    this.boardWidth = Math.max((int) Math.min(maxPuzzleWidth, width), 1);

    // Check mines is viable.
    int numCells = boardHeight * boardWidth;
    if (this.mineCount >= numCells) {
      this.mineCount = numCells - 1;
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
