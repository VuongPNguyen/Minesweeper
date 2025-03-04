package org.example.controller;

import org.example.model.Coordinate;
import org.example.model.PuzzleGenerator.PuzzleDifficulty;

public interface Controller {
  /** Handles the click action to go to the next puzzle */
  void clickNewPuzzle();
  
  /** Handles the action to set the puzzle difficulty */
  void setDifficulty(PuzzleDifficulty puzzleDifficulty);
  
  /** Handles setting puzzle parameters for custom puzzles. */
  void setPuzzleParameters(int height, int width, int mineCount);
  
  /** Handles the click action to reset the currently active puzzle */
  void clickResetPuzzle();
  
  /** Handles the click event on the cell at row r, column c */
  void clickCell(Coordinate c);
  
  /** Handles the click event for revealing adjacent cells if flags satisfy the clue. */
  void revealAdjacentCells(Coordinate c);
  
  /** Handles the right click event on the cell at row r, column c */
  void toggleFlagCell(Coordinate c);
}
