package org.example.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.example.controller.Controller;
import org.example.model.Model;

public class LibraryControlPanel implements FXComponent {
  private final Model model;
  private final Controller controller;

  public LibraryControlPanel(Model model, Controller controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox hBox = new HBox();
    hBox.getStyleClass().add("control-panel");

    Button prevPuzzleButton = new Button("←");
    prevPuzzleButton.setOnAction(e -> controller.clickPrevPuzzle());

    Label puzzleLabel =
        new Label(
            "Puzzle " + (model.getActivePuzzleIndex() + 1) + " of " + model.getPuzzleLibrarySize());

    Button nextPuzzleButton = new Button("→");
    nextPuzzleButton.setOnAction(e -> controller.clickNextPuzzle());

    hBox.getChildren().add(prevPuzzleButton);
    hBox.getChildren().add(puzzleLabel);
    hBox.getChildren().add(nextPuzzleButton);
    return hBox;
  }
}
