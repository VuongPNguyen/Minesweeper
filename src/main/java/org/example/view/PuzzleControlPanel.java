package org.example.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.example.controller.Controller;
import org.example.model.Model;

public class PuzzleControlPanel implements FXComponent {
  private final Model model;
  private final Controller controller;

  public PuzzleControlPanel(Model model, Controller controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Pane hBox = new HBox();
    hBox.getStyleClass().add("control-panel");

    Button resetButton = new Button("Reset puzzle");
    resetButton.setOnMouseClicked(_ -> controller.clickResetPuzzle());

    Button randomPuzzleButton = new Button("Random puzzle");
    randomPuzzleButton.setOnMouseClicked(_ -> controller.clickRandPuzzle());

    hBox.getChildren().add(resetButton);
    hBox.getChildren().add(randomPuzzleButton);

    return hBox;
  }
}
