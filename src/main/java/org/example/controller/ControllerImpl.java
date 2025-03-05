package org.example.controller;

import org.example.model.Coordinate;
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
    model.resetPuzzle();
  }

  @Override
  public void clickCell(Coordinate c) {
    model.revealCell(c, true);
  }
  
  @Override
  public void revealAdjacentCells(Coordinate c) {
    model.revealAdjacentCells(c);
  }
  
  @Override
  public void toggleFlagCell(Coordinate c) {
    if (model.isFlag(c)) {
      model.removeFlag(c);
    } else {
      model.addFlag(c);
    }
  }
  
}
