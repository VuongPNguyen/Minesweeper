package org.example.controller;

import org.example.model.Model;
import org.example.model.PuzzleGenerator.PuzzleDifficulty;
import org.example.model.RenderType;

public class ControllerImpl implements Controller {
  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNewPuzzle() {
    model.newPuzzle();
  }

  @Override
  public void setDifficulty(PuzzleDifficulty puzzleDifficulty) {
    if (puzzleDifficulty != model.getPuzzleDifficulty()) {
      model.setPuzzleDifficulty(puzzleDifficulty);
    }
  }
  
  @Override
  public void setPuzzleParameters(int height, int width, int mineCount) {
    model.setPuzzleParameters(height, width, mineCount);
  }
  
  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle(RenderType.CHANGE_CELL_STATE);
  }

  @Override
  public void clickCell(int r, int c) {
    model.revealCell(r, c, true);
  }
  
  @Override
  public void revealAdjacentCells(int r, int c) {
    model.revealAdjacentCells(r, c);
  }
  
  @Override
  public void toggleFlagCell(int r, int c) {
    if (model.isFlag(r, c)) {
      model.removeFlag(r, c);
    } else {
      model.addFlag(r, c);
    }
  }
  
}
