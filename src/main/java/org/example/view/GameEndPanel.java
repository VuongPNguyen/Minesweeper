package org.example.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.controller.Controller;
import org.example.model.GameState;
import org.example.model.Model;

import static org.example.view.ViewConstants.MaxScreenHeight;
import static org.example.view.ViewConstants.MaxScreenWidth;

public class GameEndPanel implements FXComponent {
  private final Model model;
  private final Controller controller;

  public GameEndPanel(Model model, Controller controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    StackPane gameEndPanel = new StackPane();
    gameEndPanel.getStyleClass().add("game-end");
    gameEndPanel.setOnMouseClicked(
        _ -> {
          if (gameEndPanel.getOpacity() == 0) {
            gameEndPanel.setOpacity(1);
          } else {
            gameEndPanel.setOpacity(0);
          }
        });

    StackPane background = new StackPane();
    background.getStyleClass().add("game-end-background");
    gameEndPanel.getChildren().add(background);

    VBox popUp = new VBox(10);
    popUp.getStyleClass().add("game-end-panel");
    popUp.setMaxHeight(MaxScreenHeight * 0.3);
    popUp.setMaxWidth(MaxScreenWidth * 0.15);
    // Game over message
    String gameState = "Error";
    if (model.getGameState() == GameState.LOSE) {
      gameState = "You lose!";
    } else if (model.getGameState() == GameState.WIN) {
      gameState = "You win!";
    }
    Label gameStateLabel = new Label(gameState);
    popUp.getChildren().add(gameStateLabel);
    gameStateLabel.setStyle("-fx-font-size: " + (int) (MaxScreenHeight * 0.05));

    // Puzzle buttons
    Button retryPuzzle = new Button("Reset Puzzle");
    retryPuzzle.setOnMouseClicked(
        _ -> {
          if (gameEndPanel.getOpacity() == 1) {
            controller.clickResetPuzzle();
          } else {
            gameEndPanel.setOpacity(1);
          }
        });
    popUp.getChildren().add(retryPuzzle);

    Button newPuzzle = new Button("New Puzzle");
    newPuzzle.setOnMouseClicked(
        _ -> {
          if (gameEndPanel.getOpacity() == 1) {
            controller.clickNewPuzzle();
          } else {
            gameEndPanel.setOpacity(1);
          }
        });
    popUp.getChildren().add(newPuzzle);

    Button showPuzzle = new Button("Show Puzzle");
    showPuzzle.setOnAction(
        _ -> {
          if (gameEndPanel.getOpacity() == 1) {
            gameEndPanel.setOpacity(0);
          } else {
            gameEndPanel.setOpacity(1);
          }
        });
    popUp.getChildren().add(showPuzzle);

    gameEndPanel.getChildren().add(popUp);

    return gameEndPanel;
  }
}
