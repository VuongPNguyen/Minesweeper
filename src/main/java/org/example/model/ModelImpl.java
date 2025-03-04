package org.example.model;

import java.util.ArrayList;
import java.util.List;
import org.example.model.CellStateMap.CellState;
import org.example.model.PuzzleGenerator.PuzzleDifficulty;

public class ModelImpl implements Model {
  private final CellStateMap cellStateMap;
  private final List<ModelObserver> modelObserverList = new ArrayList<>();
  private final PuzzleGenerator puzzleGenerator;
  private Puzzle puzzle;
  private int revealGoal;
  private GameState gameState;
  private Coordinate explodedMine;
  private PuzzleDifficulty puzzleDifficulty;
  private boolean isNewPuzzle = true;

  public ModelImpl(PuzzleDifficulty puzzleDifficulty) {
    if (puzzleDifficulty == null) {
      throw new IllegalArgumentException("puzzleDifficulty is not valid");
    }
    this.puzzleDifficulty = puzzleDifficulty;
    puzzleGenerator = new PuzzleGeneratorImpl(new CoordinateImpl(0, 0));
    puzzle = puzzleGenerator.generateRandomPuzzle(puzzleDifficulty);
    cellStateMap = new CellStateMapImpl(puzzle.getHeight(), puzzle.getWidth());
    this.resetPuzzle(RenderType.NEW_PUZZLE);
  }

  public ModelImpl() {
    this(PuzzleDifficulty.MEDIUM);
  }

  public void checkIndexInBounds(Coordinate c) {
    int puzzleHeight = this.getPuzzle().getHeight();
    int puzzleWidth = this.getPuzzle().getWidth();
    if (c.row() < 0 || c.row() >= puzzleHeight || c.col() < 0 || c.col() >= puzzleWidth) {
      throw new IndexOutOfBoundsException("Index is out of bounds of puzzle.");
    }
  }

  @Override
  public void revealCell(Coordinate c, boolean rootCell) {
    checkIndexInBounds(c);
    if (isNewPuzzle) {
      newPuzzle(c);
      isNewPuzzle = false;
    }
    if (getGameState() == GameState.LOSE || getGameState() == GameState.WIN) {
      return;
    }
    if (cellStateMap.isHide(c)) {
      cellStateMap.setCellState(c, CellState.SHOW);
      if (this.isMine(c) && rootCell) {
        revealAllMines();
        setExplodedMine(c);
        setGameState(GameState.LOSE);
      } else if (!this.isMine(c)) {
        revealGoal--;
      }
      if (this.isBlank(c)) {
        this.revealBlankAlgorithm(c);
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
  public void revealBlankAlgorithm(Coordinate c) {
    for (int row = c.row() - 1; row <= c.row() + 1; row++) {
      for (int col = c.col() - 1; col <= c.col() + 1; col++) {
        if (row != c.row() || col != c.col()) {
          try {
            revealCell(c, false);
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
    for (int r = 0; r < this.getPuzzleHeight(); r++) {
      for (int c = 0; c < this.getPuzzleWidth(); c++) {
        Coordinate coordinate = new CoordinateImpl(r, c);
        if (this.isMine(coordinate)) {
          this.revealCell(coordinate, false);
        }
      }
    }
  }

  @Override
  public void revealAdjacentCells(Coordinate c) {
    checkIndexInBounds(c);
    if (!isClue(c)) {
      throw new IllegalArgumentException("Provided cell is not a clue.");
    }
    // Check if flags satisfy clue.
    int flagCount = 0;
    for (int row = c.row() - 1; row <= c.row() + 1; row++) {
      for (int col = c.col() - 1; col <= c.col() + 1; col++) {
        if (row >= 0 && row < puzzle.getHeight() && col >= 0 && col < puzzle.getWidth()) {
          if (this.isFlag(c)) {
            flagCount++;
          }
        }
      }
    }
    if (flagCount == this.getClue(c)) {
      for (int row = c.row() - 1; row <= c.row() + 1; row++) {
        for (int col = c.col() - 1; col <= c.col() + 1; col++) {
          if (row >= 0 && row < puzzle.getHeight() && col >= 0 && col < puzzle.getWidth()) {
            revealCell(new CoordinateImpl(row, col), true);
          }
        }
      }
    }
  }

  @Override
  public void addFlag(Coordinate c) {
    checkIndexInBounds(c);
    if (getGameState() == GameState.LOSE || getGameState() == GameState.WIN) {
      return;
    }
    if (cellStateMap.isHide(c)) {
      cellStateMap.setCellState(c, CellState.FLAG);
      notify(this, RenderType.CHANGE_CELL_STATE);
    }
  }

  @Override
  public void removeFlag(Coordinate c) {
    checkIndexInBounds(c);
    if (getGameState() == GameState.LOSE || getGameState() == GameState.WIN) {
      return;
    }
    if (cellStateMap.isFlag(c)) {
      cellStateMap.setCellState(c, CellState.HIDE);
      notify(this, RenderType.CHANGE_CELL_STATE);
    }
  }

  @Override
  public void newPuzzle(Coordinate c) {
    puzzle = puzzleGenerator.generateRandomPuzzle(c);
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
  public boolean isMine(Coordinate c) {
    checkIndexInBounds(c);
    return getPuzzle().isMine(c);
  }

  @Override
  public boolean isClue(Coordinate c) {
    checkIndexInBounds(c);
    return getPuzzle().isClue(c);
  }

  @Override
  public int getClue(Coordinate c) {
    if (!isClue(c)) {
      throw new IllegalArgumentException("Cell is not CLUE.");
    }
    return getPuzzle().getClue(c);
  }

  @Override
  public Puzzle getPuzzle() {
    return puzzle;
  }

  @Override
  public void resetPuzzle(RenderType renderType) {
    revealGoal = 0;
    int puzzleHeight = puzzleGenerator.getHeight();
    int puzzleWidth = puzzleGenerator.getWidth();
    cellStateMap.newCellStateMap(puzzleHeight, puzzleWidth);
    revealGoal = puzzleHeight * puzzleWidth - puzzleGenerator.getMineCount();
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

  public void setExplodedMine(Coordinate c) {
    this.explodedMine = c;
  }

  @Override
  public void setPuzzleParameters(int height, int width, int mineCount) {
    puzzleGenerator.setPuzzleParameters(height, width, mineCount);
    this.newPuzzle();
  }

  @Override
  public int getPuzzleHeight() {
    return getPuzzle().getHeight();
  }

  @Override
  public int getPuzzleWidth() {
    return getPuzzle().getWidth();
  }

  @Override
  public int getMineCount() {
    return puzzleGenerator.getMineCount();
  }

  @Override
  public boolean isBlank(Coordinate c) {
    checkIndexInBounds(c);
    return getPuzzle().isBlank(c);
  }

  @Override
  public CellState getCellState(Coordinate c) {
    return cellStateMap.getCellState(c);
  }

  @Override
  public boolean isHide(Coordinate c) {
    return cellStateMap.getCellState(c) == CellState.HIDE;
  }

  @Override
  public boolean isFlag(Coordinate c) {
    return cellStateMap.getCellState(c) == CellState.FLAG;
  }

  @Override
  public boolean isShow(Coordinate c) {
    return cellStateMap.getCellState(c) == CellState.SHOW;
  }
}
