package org.example.view;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.model.Model;

import static org.example.view.ViewConstants.*;

public class Mine implements FXComponent {
  private final Model model;
  private final Stage stage;
  private final int row, col;
  
  public Mine(Model model, Stage stage, int row, int col) {
    this.model = model;
    this.stage = stage;
    this.row = row;
    this.col = col;
  }
  
  public Parent render() {
    StackPane background = new StackPane();
    background.getStyleClass().add("cell");
    int puzzleHeight = model.getActivePuzzle().getHeight();
    int puzzleWidth = model.getActivePuzzle().getWidth();
    double cellSize = Math.min(MaxScreenHeight/puzzleHeight, MaxScreenWidth/puzzleWidth);
    background.setMinSize(cellSize, cellSize);
    background.setMaxSize(cellSize, cellSize);
    
    ImageView mineImage = new ImageView(new Image("mine.png"));
    mineImage.setFitWidth(cellSize);
    mineImage.setFitHeight(cellSize);
    background.getChildren().add(mineImage);
    
    return background;
  }
}
