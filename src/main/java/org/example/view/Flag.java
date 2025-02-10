package org.example.view;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import org.example.controller.Controller;
import org.example.model.Model;

import static org.example.view.ViewConstants.*;
import static org.example.view.ViewConstants.gridGap;

public class Flag implements FXComponent {
  private final Model model;
  private final Controller controller;
  private final int row, col;

  public Flag(Model model, Controller controller, int row, int col) {
    this.model = model;
    this.controller = controller;
    this.row = row;
    this.col = col;
  }

  @Override
  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("flag");

    int puzzleHeight = model.getActivePuzzle().getHeight();
    int puzzleWidth = model.getActivePuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight / puzzleHeight, MaxScreenWidth / puzzleWidth);
    cellSize -= gridGap + gridGap / Math.min(puzzleHeight, puzzleWidth);
    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);
    
    ImageView flagImage = new ImageView(new Image("flag.png"));
    flagImage.setFitWidth(cellSize - 10);
    flagImage.setFitHeight(cellSize - 10);
    background.getChildren().add(flagImage);

    background.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.SECONDARY) {
        controller.toggleFlagCell(row, col);
      }
    });

    return background;
  }
}
