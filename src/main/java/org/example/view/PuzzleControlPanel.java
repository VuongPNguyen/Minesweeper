package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.example.controller.Controller;
import org.example.model.PuzzleGenerator.PuzzleDifficulty;

public class PuzzleControlPanel implements FXComponent {
  private final Controller controller;

  public PuzzleControlPanel(Controller controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    Pane hBox = new HBox();
    hBox.getStyleClass().add("control-panel");

    ObservableList<PuzzleDifficulty> puzzleDifficultiesList =
        FXCollections.observableArrayList(
            PuzzleDifficulty.EASY, PuzzleDifficulty.MEDIUM, PuzzleDifficulty.HARD);
    ComboBox<PuzzleDifficulty> difficultyComboBox = new ComboBox<>(puzzleDifficultiesList);
    difficultyComboBox.setValue(PuzzleDifficulty.MEDIUM);
    difficultyComboBox.setOnAction(_ -> controller.setDifficulty(difficultyComboBox.getValue()));
    
    Button resetButton = new Button("Reset Puzzle");
    resetButton.setOnMouseClicked(_ -> controller.clickResetPuzzle());

    Button randomPuzzleButton = new Button("New Puzzle");
    randomPuzzleButton.setOnMouseClicked(_ -> controller.clickNewPuzzle());
    
    hBox.getChildren().add(difficultyComboBox);
    hBox.getChildren().add(resetButton);
    hBox.getChildren().add(randomPuzzleButton);

    return hBox;
  }
}
