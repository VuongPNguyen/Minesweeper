package org.example.view;

import static org.example.view.ViewConstants.*;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.example.model.Model;

public class Mine implements FXComponent {
  private final Model model;

  public Mine(Model model) {
    this.model = model;
  }

  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("cell");
    int puzzleHeight = model.getPuzzle().getHeight();
    int puzzleWidth = model.getPuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight / puzzleHeight, MaxScreenWidth / puzzleWidth);
    cellSize -= gridGap + gridGap / Math.min(puzzleHeight, puzzleWidth);
    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);

    Label label = new Label("⨷");
    background.getChildren().add(label);
    background.setStyle("-fx-font-size: " + cellSize);
    return background;
  }
}
