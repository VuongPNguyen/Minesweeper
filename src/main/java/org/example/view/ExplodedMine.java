package org.example.view;

import static org.example.view.ViewConstants.*;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.example.model.Model;

public class ExplodedMine implements FXComponent {
  private final Model model;

  public ExplodedMine(Model model) {
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
    background.setStyle("-fx-font-size: " + cellSize + ";");

    Label label = new Label("⨷");
    label.getStyleClass().add("exploded-mine");

    background.getChildren().add(label);

    return background;
  }
}
