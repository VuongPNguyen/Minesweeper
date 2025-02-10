package org.example.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.controller.Controller;
import org.example.model.*;

import static org.example.view.ViewConstants.*;

public class View implements FXComponent, ModelObserver {
  private final Model model;
  private final Controller controller;
  private final Stage stage;

  public View(Model model, Controller controller, Stage stage) {
    this.model = model;
    this.controller = controller;
    this.stage = stage;
  }

  @Override
  public void update(Model model) {
    Scene scene = new Scene(render());
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
  }

  @Override
  public Parent render() {
    Pane vBox = new VBox();
    vBox.getStyleClass().add("client");
    vBox.setMaxHeight(MaxScreenHeight);
    vBox.setMaxWidth(MaxScreenWidth);
    vBox.setPrefSize(MaxScreenWidth, MaxScreenHeight);

    Label label = new Label(String.valueOf(model.getRevealTarget()));

    if (model.getGameState() == GameState.LOSE) {
      label = new Label("LOSE");
    } else if (model.getGameState() == GameState.WIN) {
      label = new Label("WIN");
    }

    StackPane puzzleArea = new StackPane();

    PuzzleGrid puzzleGrid = new PuzzleGrid(model);
    puzzleArea.getChildren().add(puzzleGrid.render());

    PlayGrid playGrid = new PlayGrid(model, controller);
    puzzleArea.getChildren().add(playGrid.render());

    vBox.getChildren().add(label);
    vBox.getChildren().add(puzzleArea);

    return vBox;
  }
}
