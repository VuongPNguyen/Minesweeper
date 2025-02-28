package org.example.view;

import static org.example.view.ViewConstants.*;
import static org.example.view.ViewConstants.gridGap;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.example.model.Model;

public class WrongFlag implements FXComponent {
  private final Model model;

  public WrongFlag(Model model) {
    this.model = model;
  }

  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("cell");
    int puzzleHeight = model.getActivePuzzle().getHeight();
    int puzzleWidth = model.getActivePuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight / puzzleHeight, MaxScreenWidth / puzzleWidth);
    cellSize -= gridGap + gridGap / Math.min(puzzleHeight, puzzleWidth);
    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);
    background.setStyle("-fx-font-size: " + cellSize * 0.75);

    Label label = new Label("❌");
    label.getStyleClass().add("wrong-flag");

    background.getChildren().add(label);

    return background;
  }
}
