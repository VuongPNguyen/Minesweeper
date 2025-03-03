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
    puzzleControlPanel = new PuzzleControlPanel(controller).render();
    puzzleArea = new StackPane();
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
    
    puzzleArea = new StackPane();

    PlayGrid playGrid = new PlayGrid(model, controller);
    puzzleArea.getChildren().add(playGrid.render());

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
