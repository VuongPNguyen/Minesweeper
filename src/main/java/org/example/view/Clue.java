package org.example.view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.example.model.Model;

import static org.example.view.ViewConstants.*;

public class Clue implements FXComponent {
  private final Model model;
  private final int row, col;

  public Clue(Model model, int row, int col) {
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

    int clueNumber = model.getActivePuzzle().getClue(row, col);
    Label clueLabel = new Label(String.valueOf(clueNumber));
    background.setStyle("-fx-font-size: " + (cellSize / 2));
    switch (clueNumber) {
      case 1:
        clueLabel.setStyle("-fx-text-fill: blue");
        break;
      case 2:
        clueLabel.setStyle("-fx-text-fill: green");
        break;
      case 3:
        clueLabel.setStyle("-fx-text-fill: red");
        break;
      case 4:
        clueLabel.setStyle("-fx-text-fill: darkblue");
        break;
      case 5:
        clueLabel.setStyle("-fx-text-fill: brown");
        break;
      case 6:
        clueLabel.setStyle("-fx-text-fill: cyan");
        break;
      case 7:
        clueLabel.setStyle("-fx-text-fill: black");
        break;
      case 8:
        clueLabel.setStyle("-fx-text-fill: gray");
        break;
    }
    background.getChildren().add(clueLabel);

    return background;
  }
}
