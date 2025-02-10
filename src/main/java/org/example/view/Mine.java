package org.example.view;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.example.model.Model;

import java.util.Arrays;

import static org.example.view.ViewConstants.*;

public class Mine implements FXComponent {
  private final Model model;
  private final int row, col;

  public Mine(Model model, int row, int col) {
    this.model = model;
    this.row = row;
    this.col = col;
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

    ImageView mineImage = new ImageView(new Image("mine.png"));
    mineImage.setFitWidth(cellSize - 10);
    mineImage.setFitHeight(cellSize - 10);
    background.getChildren().add(mineImage);

    if (Arrays.equals(model.getExplodedMine(), new int[] {row, col})) {
      background.setStyle("-fx-background-color: red");
    }

    return background;
  }
}
