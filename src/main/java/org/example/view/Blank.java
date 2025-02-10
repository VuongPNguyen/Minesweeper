package org.example.view;

import static org.example.view.ViewConstants.*;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.example.model.Model;

public class Blank implements FXComponent {
  private final Model model;
  private final int row, col;

  public Blank(Model model, int row, int col) {
    this.model = model;
    this.row = row;
    this.col = col;
  }

  @Override
  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("cell");

    int puzzleHeight = model.getActivePuzzle().getHeight();
    int puzzleWidth = model.getActivePuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight / puzzleHeight, MaxScreenWidth / puzzleWidth);
    cellSize -= gridGap + gridGap / Math.min(puzzleHeight, puzzleWidth);
    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);

    return background;
  }
}
