package org.example.view;

import static org.example.view.ViewConstants.*;
import static org.example.view.ViewConstants.gridGap;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.example.model.Model;

public class EmptyCell implements FXComponent {
  private final Model model;

  public EmptyCell(Model model) {
    this.model = model;
  }

  @Override
  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("show");

    int puzzleHeight = model.getActivePuzzle().getHeight();
    int puzzleWidth = model.getActivePuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight / puzzleHeight, MaxScreenWidth / puzzleWidth);
    cellSize -= gridGap + gridGap / Math.min(puzzleHeight, puzzleWidth);
    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);

    return background;
  }
}
