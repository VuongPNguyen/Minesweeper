package org.example.view;

import static org.example.view.ViewConstants.*;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.example.controller.Controller;
import org.example.model.Coordinate;
import org.example.model.Model;

public class Clue implements FXComponent {
  private final Model model;
  private final Controller controller;
  private final Coordinate coordinate;

  public Clue(Model model, Controller controller, Coordinate coordinate) {
    this.model = model;
    this.controller = controller;
    this.coordinate = coordinate;
  }

  @Override
  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("cell");

    int puzzleHeight = model.getPuzzle().getHeight();
    int puzzleWidth = model.getPuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight / puzzleHeight, MaxScreenWidth / puzzleWidth);
    cellSize -= gridGap + gridGap / Math.min(puzzleHeight, puzzleWidth);

    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);

    int clueNumber = model.getClue(coordinate);
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
    
    background.setOnMouseClicked(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY) { // May also use MIDDLE
            controller.revealAdjacentCells(coordinate);
          }
        });
    
    background.getChildren().add(clueLabel);

    return background;
  }
}
