package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private final PuzzleLibrary puzzleLibrary;
  private int puzzleIndex = 0;
  private CellState[][] cellStateMap;
  private int revealTarget;
  private final List<ModelObserver> modelObserverList = new ArrayList<>();
  private GameState gameState;
  private int[] explodedMine = new int[] {-1, -1};

  public ModelImpl(PuzzleLibrary puzzleLibrary) {
    if (puzzleLibrary == null) {
      throw new IllegalArgumentException("PuzzleLibrary is null");
    }
    this.puzzleLibrary = puzzleLibrary;
    this.resetPuzzle(RenderType.NEW_PUZZLE);
  }

  public void checkIndexInBounds(int r, int c) {
    int puzzleHeight = this.getActivePuzzle().getHeight();
    int puzzleWidth = this.getActivePuzzle().getWidth();
    if (r < 0 || r >= puzzleHeight || c < 0 || c >= puzzleWidth) {
      throw new IndexOutOfBoundsException("Index is out of bounds of puzzle.");
    }
  }

  @Override
  public void revealCell(int r, int c, boolean rootCell) {
    checkIndexInBounds(r, c);
    if (getGameState() == GameState.LOSE || getGameState() == GameState.WIN) {
      return;
    }
    if (cellStateMap[r][c] == CellState.HIDE) {
      cellStateMap[r][c] = CellState.SHOW;
      if (this.isMine(r, c)) {
        revealAllMines();
        setExplodedMine(new int[] {r, c});
        setGameState(GameState.LOSE);
      } else {
        revealTarget--;
      }
      Puzzle activePuzzle = this.getActivePuzzle();
      if (activePuzzle.getCellType(r, c) == CellType.BLANK) {
        this.revealBlankAlgorithm(r, c);
      }
      this.updateGameState();
      if (this.isMine(r, c)) {
        notify(this, RenderType.CHANGE_PUZZLE_CELL);
      } else if (rootCell) {
        notify(this, RenderType.CHANGE_CELL_STATE);
      }
    }
  }

  @Override
  public void revealBlankAlgorithm(int r, int c) {
    for (int row = r - 1; row <= r + 1; row++) {
      for (int col = c - 1; col <= c + 1; col++) {
        if (row != r || col != c) {
          try {
            revealCell(row, col, false);
          } catch (IndexOutOfBoundsException ignored) {
          } catch (Exception e) {
            throw new RuntimeException("Blank reveal algorithm failure.");
          }
        }
      }
    }
  }

  private void revealAllMines() {
    Puzzle activePuzzle = this.getActivePuzzle();
    int puzzleHeight = activePuzzle.getHeight();
    int puzzleWidth = activePuzzle.getWidth();
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        if (activePuzzle.getCellType(r, c) == CellType.MINE) {
          this.revealCell(r, c, false);
        }
      }
    }
  }

  @Override
  public void addFlag(int r, int c) {
    checkIndexInBounds(r, c);
    if (getGameState() == GameState.LOSE || getGameState() == GameState.WIN) {
      return;
    }
    if (this.getCellState(r, c) == CellState.HIDE) {
      cellStateMap[r][c] = CellState.FLAG;
      notify(this, RenderType.CHANGE_CELL_STATE);
    }
  }

  @Override
  public void removeFlag(int r, int c) {
    checkIndexInBounds(r, c);
    if (getGameState() == GameState.LOSE || getGameState() == GameState.WIN) {
      return;
    }
    if (this.isFlag(r, c)) {
      cellStateMap[r][c] = CellState.HIDE;
      notify(this, RenderType.CHANGE_CELL_STATE);
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
    this.resetPuzzle(RenderType.NEW_PUZZLE);
  }

  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  @Override
  public void resetPuzzle(RenderType renderType) {
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
    this.setGameState(GameState.PLAYING);
    notify(this, renderType);
  }

  @Override
  public int getRevealTarget() {
    return revealTarget;
  }

  @Override
  public void updateGameState() {
    if (this.getGameState() == GameState.LOSE) {
      return;
    } else if (this.getRevealTarget() == 0) {
      this.setGameState(GameState.WIN);
    }
  }

  @Override
  public GameState getGameState() {
    return gameState;
  }

  @Override
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    modelObserverList.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    modelObserverList.remove(observer);
  }

  public void notify(Model model, RenderType renderType) {
    for (ModelObserver o : modelObserverList) {
      o.update(model, renderType);
    }
  }

  public int[] getExplodedMine() {
    return explodedMine;
  }

  public void setExplodedMine(int[] coordinates) {
    this.explodedMine = coordinates;
  }
}
