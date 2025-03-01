package org.example.controller;

import org.example.model.PuzzleGenerator.PuzzleDifficulty;

public interface Controller {
  /** Handles the click action to go to the next puzzle */
  void clickNewPuzzle();
  
  /** Handles the action to set the puzzle difficulty */
  void setDifficulty(PuzzleDifficulty puzzleDifficulty);
  
  /** Handles the click action to reset the currently active puzzle */
  void clickResetPuzzle();
  
  /** Handles the click event on the cell at row r, column c */
  void clickCell(int r, int c);
  
  /** Handles the right click event on the cell at row r, column c */
  void toggleFlagCell(int r, int c);
}
