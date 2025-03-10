package org.example.view;

import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.example.controller.Controller;
import org.example.model.Coordinate;
import org.example.model.Model;

import static org.example.view.ViewConstants.*;

public class Hide implements FXComponent {
  private final Model model;
  private final Controller controller;
  private final Coordinate coordinate;

  public Hide(Model model, Controller controller, Coordinate coordinate) {
    this.model = model;
    this.controller = controller;
    this.coordinate = coordinate;
  }

  @Override
  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("hide");

    int puzzleHeight = model.getPuzzle().getHeight();
    int puzzleWidth = model.getPuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight / puzzleHeight, MaxScreenWidth / puzzleWidth);
    cellSize -= gridGap + gridGap / Math.min(puzzleHeight, puzzleWidth);
    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);

    background.setOnMouseClicked(
        e -> {
          if (e.getButton() == MouseButton.PRIMARY) {
            controller.clickCell(coordinate);
          } else if (e.getButton() == MouseButton.SECONDARY) {
            controller.toggleFlagCell(coordinate);
          }
        });

    return background;
  }
}
