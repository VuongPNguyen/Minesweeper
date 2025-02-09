package org.example.controller;

import org.example.model.Model;
import org.example.model.Puzzle;

import java.util.Random;

public class ControllerImpl implements Controller {
  private final Model model;
  
  public ControllerImpl(Model model) {
    this.model = model;
  }
  
  @Override
  public void clickNextPuzzle() {
    int nextPuzzleIndex = model.getActivePuzzleIndex() + 1;
    if (nextPuzzleIndex < model.getPuzzleLibrarySize()) {
      model.setActivePuzzleIndex(nextPuzzleIndex);
    }
  }
  
  @Override
  public void clickPrevPuzzle() {
    int prevPuzzleIndex = model.getActivePuzzleIndex() - 1;
    if (prevPuzzleIndex >= 0) {
      model.setActivePuzzleIndex(prevPuzzleIndex);
    }
  }
  
  @Override
  public void clickRandPuzzle() {
    Random rn = new Random();
    int randInt = rn.nextInt(model.getPuzzleLibrarySize());
    while (randInt == model.getActivePuzzleIndex()) {
      randInt = rn.nextInt(model.getPuzzleLibrarySize());
    }
    model.setActivePuzzleIndex(randInt);
  }
  
  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }
  
  @Override
  public void clickCell(int r, int c) {
    model.revealCell(r, c);
  }
  
  @Override
  public void toggleFlagCell(int r, int c) {
    if (model.isFlag(r, c)) {
      model.removeFlag(r, c);
    } else {
      model.addFlag(r, c);
    }
  }
  
  @Override
  public boolean isRevealed(int r, int c) {
    return model.isRevealed(r, c);
  }
  
  @Override
  public boolean isMine(int r, int c) {
    return model.isMine(r, c);
  }
  
  @Override
  public boolean isSolved() {
    return model.isSolved();
  }
  
  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }
}
