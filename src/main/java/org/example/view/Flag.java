package org.example.view;

import static org.example.view.ViewConstants.*;
import static org.example.view.ViewConstants.gridGap;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.example.controller.Controller;
import org.example.model.Coordinate;
import org.example.model.Model;

public class Flag implements FXComponent {
  private final Model model;
  private final Controller controller;
  private final Coordinate coordinate;

  public Flag(Model model, Controller controller, Coordinate coordinate) {
    this.model = model;
    this.controller = controller;
    this.coordinate = coordinate;
  }

  @Override
  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("flag");

    int puzzleHeight = model.getPuzzle().getHeight();
    int puzzleWidth = model.getPuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight / puzzleHeight, MaxScreenWidth / puzzleWidth);
    cellSize -= gridGap + gridGap / Math.min(puzzleHeight, puzzleWidth);
    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);
    
    Label flag = new Label("⚑");
    background.getChildren().add(flag);
    background.setStyle("-fx-font-size: " + cellSize);

    background.setOnMouseClicked(
        e -> {
          if (e.getButton() == MouseButton.SECONDARY) {
            controller.toggleFlagCell(coordinate);
          }
        });

    return background;
  }
}
