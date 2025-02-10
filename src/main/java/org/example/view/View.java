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
  private Node puzzleGrid;
  private Node playGrid;

  public View(Model model, Controller controller, Stage stage) {
    this.model = model;
    this.controller = controller;
    this.stage = stage;

    // FXComponents
    libraryControlPanel = new LibraryControlPanel(model, controller).render();
    puzzleControlPanel = new PuzzleControlPanel(model, controller).render();
  }

  @Override
  public void update(Model model) {
    Scene scene = new Scene(this.render());
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);
    stage.setMaximized(true);
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

    puzzleGrid = new PuzzleGrid(model).render();
    puzzleArea.getChildren().add(puzzleGrid);

    playGrid = new PlayGrid(model, controller).render();
    puzzleArea.getChildren().add(playGrid);

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
    
    vBox.getChildren().add(libraryControlPanel);
    vBox.getChildren().add(puzzleControlPanel);
    
    switch (renderType) {
      case NEW_PUZZLE -> {
        libraryControlPanel = new LibraryControlPanel(model, controller).render();
        
        puzzleArea = new StackPane();
        
        puzzleGrid = new PuzzleGrid(model).render();
        puzzleArea.getChildren().add(puzzleGrid);
        
        playGrid = new PlayGrid(model, controller).render();
        puzzleArea.getChildren().add(playGrid);
        
        vBox.getChildren().add(puzzleArea);
        
        return vBox;
      }
      case RESET_PUZZLE -> {
        puzzleArea = new StackPane();
        
        puzzleArea.getChildren().add(puzzleGrid);

        playGrid = new PlayGrid(model, controller).render();
        puzzleArea.getChildren().add(playGrid);
        
        vBox.getChildren().add(puzzleArea);
        
        return vBox;
      }
    }
    
    return vBox;
  }
}
