package org.example.view;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.example.controller.Controller;
import org.example.model.Model;

import static org.example.view.ViewConstants.*;
import static org.example.view.ViewConstants.gridGap;

public class Show implements FXComponent {
  private final Model model;

  public Show(Model model) {
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
