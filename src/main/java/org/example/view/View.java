package org.example.view;

import static org.example.view.ViewConstants.*;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.controller.Controller;
import org.example.model.*;

public class View implements FXComponent, ModelObserver {
  private final Model model;
  private final Controller controller;
  private final Stage stage;
  // FXComponent Nodes
  private Node libraryControlPanel;
  private final Node puzzleControlPanel;
  private StackPane puzzleArea = new StackPane();
  private Node playGrid;

  public View(Model model, Controller controller, Stage stage) {
    this.model = model;
    this.controller = controller;
    this.stage = stage;

    // FXComponents
    libraryControlPanel = new LibraryControlPanel(model, controller).render();
    puzzleControlPanel = new PuzzleControlPanel(model, controller).render();
  }

  public void update(Model model, RenderType renderType) {
    Scene scene = new Scene(this.render(renderType));
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.setMaximized(true);
  }

  @Override
  public Parent render() {
    Pane vBox = new VBox();
    vBox.getStyleClass().add("client");
    vBox.setMaxHeight(MaxScreenHeight);
    vBox.setMaxWidth(MaxScreenWidth);
    vBox.setPrefSize(MaxScreenWidth, MaxScreenHeight);

    /**
     * USE LATER if (model.getGameState() == GameState.LOSE) { } else if (model.getGameState() ==
     * GameState.WIN) { }
     */
    puzzleArea = new StackPane();

    PlayGrid playGrid = new PlayGrid(model, controller);
    puzzleArea.getChildren().add(playGrid.render());

    vBox.getChildren().add(libraryControlPanel);
    vBox.getChildren().add(puzzleControlPanel);
    vBox.getChildren().add(puzzleArea);

    return vBox;
  }

  public Parent render(RenderType renderType) {
    Pane vBox = new VBox();
    vBox.getStyleClass().add("client");
    vBox.setMaxHeight(MaxScreenHeight);
    vBox.setMaxWidth(MaxScreenWidth);
    vBox.setPrefSize(MaxScreenWidth, MaxScreenHeight);

    switch (renderType) {
      case NEW_PUZZLE -> {
        // Re-render
        libraryControlPanel = new LibraryControlPanel(model, controller).render();
        vBox.getChildren().add(libraryControlPanel);
        vBox.getChildren().add(puzzleControlPanel);

        puzzleArea = new StackPane();

        PlayGrid playGrid = new PlayGrid(model, controller);
        puzzleArea.getChildren().add(playGrid.render());

        vBox.getChildren().add(puzzleArea);
        return vBox;
      }
      case CHANGE_CELL_STATE -> {
        vBox.getChildren().add(libraryControlPanel);
        vBox.getChildren().add(puzzleControlPanel);

        puzzleArea = new StackPane();

        playGrid = new PlayGrid(model, controller).render();
        puzzleArea.getChildren().add(playGrid);

        vBox.getChildren().add(puzzleArea);

        return vBox;
      }
      case TRIGGER_MINES -> {
        vBox.getChildren().add(libraryControlPanel);
        vBox.getChildren().add(puzzleControlPanel);

        puzzleArea = new StackPane();

        puzzleArea.getChildren().add(playGrid);

        PlayGridRevealMines playGridRevealMines = new PlayGridRevealMines(model);
        puzzleArea.getChildren().add(playGridRevealMines.render());

        vBox.getChildren().add(puzzleArea);

        return vBox;
      }
    }

    return vBox;
  }
}
