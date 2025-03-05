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
  private final Node puzzleControlPanel;
  private StackPane puzzleArea;
  
  public View(Model model, Controller controller, Stage stage) {
    this.model = model;
    this.controller = controller;
    this.stage = stage;

    // FXComponents
    puzzleControlPanel = new PuzzleControlPanel(model, controller).render();
    puzzleArea = new StackPane();
  }

  public void update(Model model) {
    Scene scene = new Scene(this.render());
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.setMaximized(true);
  }

  @Override
  public Parent render() {
    Pane client = new VBox();
    client.getStyleClass().add("client");
    client.setMaxHeight(MaxScreenHeight);
    client.setMaxWidth(MaxScreenWidth);
    client.setPrefSize(MaxScreenWidth, MaxScreenHeight);
    client.setPrefHeight(screen.getHeight() - 20);

    PuzzleControlPanel pcp = new PuzzleControlPanel(model, controller);
    CustomPuzzlePanel cpp = new CustomPuzzlePanel(model, controller);

    StackPane puzzleArea = new StackPane();

    PlayGrid pg = new PlayGrid(model, controller);
    puzzleArea.getChildren().add(pg.render());

    GameEndPanel gameEndPanel = new GameEndPanel(model, controller);
    if (model.getGameState() != GameState.PLAYING) {
      puzzleArea.getChildren().add(gameEndPanel.render());
    }

    client.getChildren().add(pcp.render());
    client.getChildren().add(cpp.render());
    client.getChildren().add(puzzleArea);

    return client;
  }

  public Parent render(RenderType renderType) {
    Pane vBox = new VBox();
    vBox.getStyleClass().add("client");
    vBox.setMaxHeight(MaxScreenHeight);
    vBox.setMaxWidth(MaxScreenWidth);
    vBox.setPrefSize(MaxScreenWidth, MaxScreenHeight);
    vBox.setPrefHeight(screen.getHeight() - 20);

    switch (renderType) {
      case NEW_PUZZLE -> {
        // Re-render
        vBox.getChildren().add(puzzleControlPanel);
        
        CustomPuzzlePanel customPuzzlePanel = new CustomPuzzlePanel(model, controller);
        vBox.getChildren().add(customPuzzlePanel.render());

        puzzleArea = new StackPane();

        PlayGrid playGrid = new PlayGrid(model, controller);
        puzzleArea.getChildren().add(playGrid.render());

        vBox.getChildren().add(puzzleArea);
        return vBox;
      }
      case CHANGE_CELL_STATE -> {
        vBox.getChildren().add(puzzleControlPanel);
        
        CustomPuzzlePanel customPuzzlePanel = new CustomPuzzlePanel(model, controller);
        vBox.getChildren().add(customPuzzlePanel.render());

        puzzleArea = new StackPane();
        
        Node playGrid = new PlayGrid(model, controller).render();
        puzzleArea.getChildren().add(playGrid);

        vBox.getChildren().add(puzzleArea);

        return vBox;
      }
      case END_GAME -> {
        vBox.getChildren().add(puzzleControlPanel);
        
        CustomPuzzlePanel customPuzzlePanel = new CustomPuzzlePanel(model, controller);
        vBox.getChildren().add(customPuzzlePanel.render());
        
        puzzleArea = new StackPane();
        Node playGrid = new PlayGrid(model, controller).render();
        puzzleArea.getChildren().add(playGrid);
        
        GameEndPanel gameEndPanel = new GameEndPanel(model, controller);
        puzzleArea.getChildren().add(gameEndPanel.render());
        
        vBox.getChildren().add(puzzleArea);
        
        return vBox;
      }
    }

    return vBox;
  }
}
