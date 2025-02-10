package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary puzzleLibrary;
  private int puzzleIndex = 0;
  private CellState[][] cellStateMap;
  private int revealTarget;
  private final List<ModelObserver> modelObserverList = new ArrayList<>();

  public ModelImpl(PuzzleLibrary puzzleLibrary) {
    if (puzzleLibrary == null) {
      throw new IllegalArgumentException("PuzzleLibrary is null");
    }
    this.puzzleLibrary = puzzleLibrary;
    this.resetPuzzle();
  }
  
  public void checkIndexInBounds(int r, int c) {
    int puzzleHeight = this.getActivePuzzle().getHeight();
    int puzzleWidth = this.getActivePuzzle().getWidth();
    if (r < 0 || r >= puzzleHeight || c < 0 || c >= puzzleWidth) {
      throw new IndexOutOfBoundsException("Index is out of bounds of puzzle.");
    }
  }

  @Override
  public void revealCell(int r, int c) {
    checkIndexInBounds(r, c);
    if (cellStateMap[r][c] == CellState.HIDE) {
      cellStateMap[r][c] = CellState.SHOW;
      if (!this.isMine(r, c)) {
        revealTarget--;
      }
      Puzzle activePuzzle = this.getActivePuzzle();
      if (activePuzzle.getCellType(r, c) == CellType.BLANK) {
        for (int row = r - 1; row <= r + 1; row++) {
          for (int col = c - 1; col <= c + 1; col++) {
            if (row >= 0 && row < activePuzzle.getHeight() && col >=0 && col < activePuzzle.getWidth()) {
              revealCell(row, col);
            }
          }
        }
      }
      notify(this);
    }
  }

  @Override
  public void addFlag(int r, int c) {
    checkIndexInBounds(r, c);
    if (this.getCellState(r, c) == CellState.HIDE) {
      cellStateMap[r][c] = CellState.FLAG;
      notify(this);
    }
  }

  @Override
  public void removeFlag(int r, int c) {
    checkIndexInBounds(r, c);
    if (this.isFlag(r, c)) {
      cellStateMap[r][c] = CellState.HIDE;
      notify(this);
    }
  }

  @Override
  public boolean isRevealed(int r, int c) {
    checkIndexInBounds(r, c);
    return this.getCellState(r, c) == CellState.SHOW;
  }

  @Override
  public boolean isFlag(int r, int c) {
    checkIndexInBounds(r, c);
    return this.getCellState(r, c) == CellState.FLAG;
  }

  @Override
  public boolean isMine(int r, int c) {
    checkIndexInBounds(r, c);
    return getActivePuzzle().getCellType(r, c) == CellType.MINE;
  }

  @Override
  public CellState getCellState(int r, int c) {
    checkIndexInBounds(r, c);
    return cellStateMap[r][c];
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzleLibrary.getPuzzle(this.getActivePuzzleIndex());
  }

  @Override
  public int getActivePuzzleIndex() {
    return puzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= getPuzzleLibrarySize()) {
      throw new IndexOutOfBoundsException("Index out of bounds of PuzzleLibrary.");
    }
    this.puzzleIndex = index;
    this.resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }
  
  @Override
  public void resetPuzzle() {
    revealTarget = 0;
    int puzzleHeight = this.getActivePuzzle().getHeight();
    int puzzleWidth = this.getActivePuzzle().getWidth();
    cellStateMap = new CellState[puzzleHeight][puzzleWidth];
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        cellStateMap[r][c] = CellState.HIDE;
        if (!this.isMine(r, c)) {
          revealTarget++;
        }
      }
    }
    notify(this);
  }
  
  @Override
  public int getRevealTarget() {
    return revealTarget;
  }

  @Override
  public boolean isSolved() {
    return revealTarget == 0;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    modelObserverList.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    modelObserverList.remove(observer);
  }

  public void notify(Model model) {
    for (ModelObserver o : modelObserverList) {
      o.update(model);
    }
  }
}
