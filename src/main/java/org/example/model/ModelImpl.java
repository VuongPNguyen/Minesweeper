package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary puzzleLibrary;
  private int puzzleIndex = 0;
  private CellState[][] cellStateMap;
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
    if (cellStateMap[r][c] == CellState.HIDDEN) {
      cellStateMap[r][c] = CellState.REVEALED;
      notify(this);
    }
  }

  @Override
  public void addFlag(int r, int c) {
    checkIndexInBounds(r, c);
    if (this.getCellState(r, c) == CellState.HIDDEN) {
      cellStateMap[r][c] = CellState.FLAGGED;
      notify(this);
    }
  }

  @Override
  public void removeFlag(int r, int c) {
    checkIndexInBounds(r, c);
    if (this.getCellState(r, c) == CellState.FLAGGED) {
      cellStateMap[r][c] = CellState.HIDDEN;
      notify(this);
    }
  }

  @Override
  public boolean isRevealed(int r, int c) {
    checkIndexInBounds(r, c);
    return this.getCellState(r, c) == CellState.REVEALED;
  }

  @Override
  public boolean isFlag(int r, int c) {
    checkIndexInBounds(r, c);
    return this.getCellState(r, c) == CellState.FLAGGED;
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
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle() {
    int puzzleHeight = this.getActivePuzzle().getHeight();
    int puzzleWidth = this.getActivePuzzle().getWidth();
    cellStateMap = new CellState[puzzleHeight][puzzleWidth];
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        cellStateMap[r][c] = CellState.HIDDEN;
      }
    }
  }

  @Override
  public boolean isSolved() {
    return false;
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
