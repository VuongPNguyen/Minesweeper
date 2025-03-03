package org.example.model;

import java.util.ArrayList;
import java.util.List;
import org.example.model.PuzzleGenerator.PuzzleDifficulty;

public class ModelImpl implements Model {
  private Puzzle puzzle;
  private CellState[][] cellStateMap;
  private int revealGoal;
  private final List<ModelObserver> modelObserverList = new ArrayList<>();
  private GameState gameState;
  private Coordinate explodedMine;
  private final PuzzleGenerator puzzleGenerator;
  private PuzzleDifficulty puzzleDifficulty;
  private boolean isNewPuzzle = true;

  public ModelImpl(PuzzleDifficulty puzzleDifficulty) {
    if (puzzleDifficulty == null) {
      throw new IllegalArgumentException("puzzleDifficulty is not valid");
    }
    this.puzzleDifficulty = puzzleDifficulty;
    puzzleGenerator = new PuzzleGeneratorImpl(new CoordinateImpl(0, 0));
    puzzle = puzzleGenerator.generateRandomPuzzle(puzzleDifficulty);
    this.resetPuzzle(RenderType.NEW_PUZZLE);
  }

  public ModelImpl() {
    this(PuzzleDifficulty.MEDIUM);
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
    if (isNewPuzzle) {
      newPuzzle(r, c);
      isNewPuzzle = false;
    }
    if (getGameState() == GameState.LOSE || getGameState() == GameState.WIN) {
      return;
    }
    if (cellStateMap[r][c] == CellState.HIDE) {
      cellStateMap[r][c] = CellState.SHOW;
      if (this.isMine(r, c) && rootCell) {
        revealAllMines();
        setExplodedMine(r, c);
        setGameState(GameState.LOSE);
      } else if (!this.isMine(r, c)) {
        revealGoal--;
      }
      Puzzle activePuzzle = this.getActivePuzzle();
      if (activePuzzle.getCellType(r, c) == CellType.BLANK) {
        this.revealBlankAlgorithm(r, c);
      }
      this.updateGameState();
      if (rootCell) {
        notify(this, RenderType.CHANGE_CELL_STATE);
      }
      if (getGameState() == GameState.LOSE || getGameState() == GameState.WIN) {
        notify(this, RenderType.END_GAME);
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

  @Override
  public void revealAllMines() {
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
  public void revealAdjacentCells(int r, int c) {
    checkIndexInBounds(r, c);
    if (!isClue(r, c)) {
      throw new IllegalArgumentException("This cell is not a clue");
    }
    // Check if flags satisfy clue.
    int flagCount = 0;
    for (int row = r - 1; row <= r + 1; row++) {
      for (int col = c - 1; col <= c + 1; col++) {
        if (row >= 0 && row < puzzle.getHeight() && col >= 0 && col < puzzle.getWidth()) {
          if (isFlag(row, col)) {
            flagCount++;
          }
        }
      }
    }
    if (flagCount == puzzle.getClue(r, c)) {
      for (int row = r - 1; row <= r + 1; row++) {
        for (int col = c - 1; col <= c + 1; col++) {
          if (row >= 0 && row < puzzle.getHeight() && col >= 0 && col < puzzle.getWidth()) {
            revealCell(row, col, true);
          }
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
  public void newPuzzle(int row, int col) {
    puzzle = puzzleGenerator.generateRandomPuzzle(new CoordinateImpl(row, col));
    resetPuzzle(RenderType.NEW_PUZZLE);
    isNewPuzzle = true;
    setGameState(GameState.PLAYING);
  }
  
  @Override
  public void newPuzzle(PuzzleDifficulty puzzleDifficulty) {
    this.puzzleDifficulty = puzzleDifficulty;
    puzzle = puzzleGenerator.generateRandomPuzzle(getPuzzleDifficulty(), new CoordinateImpl(0, 0));
    resetPuzzle(RenderType.NEW_PUZZLE);
    isNewPuzzle = true;
    setGameState(GameState.PLAYING);
  }
  
  @Override
  public void newPuzzle() {
    puzzle = puzzleGenerator.generateRandomPuzzle(new CoordinateImpl(0, 0));
    resetPuzzle(RenderType.NEW_PUZZLE);
    isNewPuzzle = true;
    setGameState(GameState.PLAYING);
  }

  @Override
  public PuzzleDifficulty getPuzzleDifficulty() {
    return puzzleDifficulty;
  }

  @Override
  public void setPuzzleDifficulty(PuzzleDifficulty puzzleDifficulty) {
    if (this.puzzleDifficulty != puzzleDifficulty) {
      this.newPuzzle(puzzleDifficulty);
    }
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
  public boolean isClue(int r, int c) {
    checkIndexInBounds(r, c);
    return getActivePuzzle().getCellType(r, c) == CellType.CLUE;
  }

  @Override
  public CellState getCellState(int r, int c) {
    checkIndexInBounds(r, c);
    return cellStateMap[r][c];
  }

  @Override
  public Puzzle getActivePuzzle() {
    return puzzle;
  }

  @Override
  public void resetPuzzle(RenderType renderType) {
    revealGoal = 0;
    int puzzleHeight = puzzleGenerator.getHeight();
    int puzzleWidth = puzzleGenerator.getWidth();
    cellStateMap = new CellState[puzzleHeight][puzzleWidth];
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        cellStateMap[r][c] = CellState.HIDE;
        if (!this.isMine(r, c)) {
          revealGoal++;
        }
      }
    }
    this.setGameState(GameState.PLAYING);
    notify(this, renderType);
  }

  @Override
  public int getRevealGoal() {
    return revealGoal;
  }

  @Override
  public void updateGameState() {
    if (this.getRevealGoal() == 0) {
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

  public Coordinate getExplodedMine() {
    return explodedMine;
  }
  
  public void setExplodedMine(int row, int col) {
    this.explodedMine = new CoordinateImpl(row, col);
  }
  
  @Override
  public void setPuzzleParameters(int height, int width, int mineCount) {
    puzzleGenerator.setPuzzleParameters(height, width, mineCount);
    this.newPuzzle();
  }
  
  @Override
  public int getMineCount() {
    return puzzleGenerator.getMineCount();
  }
}
