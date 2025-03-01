package org.example.controller;

import org.example.model.Model;
import org.example.model.Puzzle;
import org.example.model.RenderType;

import java.util.Random;

public class ControllerImpl implements Controller {
  private final Model model;
  
  public ControllerImpl(Model model) {
    this.model = model;
  }
  
  @Override
  public void clickNewPuzzle() {
  
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
  public void toggleFlagCell(int r, int c) {
    if (model.isFlag(r, c)) {
      model.removeFlag(r, c);
    } else {
      model.addFlag(r, c);
    }
  }
}
