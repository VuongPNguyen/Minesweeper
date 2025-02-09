package org.example.controller;

import org.example.model.Puzzle;

public interface Controller {
  /** Handles the click action to go to the next puzzle */
  void clickNextPuzzle();
  
  /** Handles the click action to go to the previous puzzle */
  void clickPrevPuzzle();
  
  /** Handles the click action to go to a random puzzle */
  void clickRandPuzzle();
  
  /** Handles the click action to reset the currently active puzzle */
  void clickResetPuzzle();
  
  /** Handles the click event on the cell at row r, column c */
  void clickCell(int r, int c);
  
  /** Handles the right click event on the cell at row r, column c */
  void toggleFlagCell(int r, int c);
}
