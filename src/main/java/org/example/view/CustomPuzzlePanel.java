package org.example.view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.controller.Controller;
import org.example.model.Model;
import org.example.model.PuzzleGenerator;

public class CustomPuzzlePanel implements FXComponent {
  private final Model model;
  private final Controller controller;

  public CustomPuzzlePanel(Model model, Controller controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox customPuzzlePanel = new HBox();
    customPuzzlePanel.getStyleClass().add("control-panel");
    customPuzzlePanel.setMinHeight(25);

    // Height
    customPuzzlePanel.getChildren().add(new Label("Height: "));
    
    TextField puzzleHeightField = new TextField();
    puzzleHeightField.setPromptText("1-99");
    puzzleHeightField.setText(String.valueOf(model.getPuzzle().getHeight()));
    puzzleHeightField.setMaxWidth(40);
    
    if (model.getPuzzleDifficulty() == PuzzleGenerator.PuzzleDifficulty.CUSTOM) {
      customPuzzlePanel.getChildren().add(puzzleHeightField);
    } else {
      Label puzzleHeightLabel = new Label(String.valueOf(model.getPuzzle().getHeight()));
      puzzleHeightLabel.setMaxWidth(40);

      customPuzzlePanel.getChildren().add(puzzleHeightLabel);
    }
    // " ✕ "
    customPuzzlePanel.getChildren().add(new Label(" ✕ "));

    // Width
    customPuzzlePanel.getChildren().add(new Label("Width: "));
    
    TextField puzzleWidthField = new TextField();
    puzzleWidthField.setPromptText("1-99");
    puzzleWidthField.setText(String.valueOf(model.getPuzzle().getWidth()));
    puzzleWidthField.setMaxWidth(40);
    
    if (model.getPuzzleDifficulty() == PuzzleGenerator.PuzzleDifficulty.CUSTOM) {
      customPuzzlePanel.getChildren().add(puzzleWidthField);
    } else {
      Label puzzleWidthLabel = new Label(String.valueOf(model.getPuzzle().getWidth()));
      puzzleWidthLabel.setMaxWidth(40);

      customPuzzlePanel.getChildren().add(puzzleWidthLabel);
    }
    
    // Mines
    customPuzzlePanel.getChildren().add(new Label(" | "));
    customPuzzlePanel.getChildren().add(new Label("Mines: "));
    
    TextField puzzleMinesField = new TextField();
    puzzleMinesField.setText(String.valueOf(model.getMineCount()));
    puzzleMinesField.setMaxWidth(40);
    
    if (model.getPuzzleDifficulty() == PuzzleGenerator.PuzzleDifficulty.CUSTOM) {
      customPuzzlePanel.getChildren().add(puzzleMinesField);
    } else {
      Label puzzleMineLabel = new Label(String.valueOf(model.getMineCount()));
      puzzleMineLabel.setMaxWidth(40);
      
      customPuzzlePanel.getChildren().add(puzzleMineLabel);
    }
    puzzleHeightField.setOnAction(
        _ -> controller.setPuzzleParameters(
            Integer.parseInt(puzzleHeightField.getText()),
            Integer.parseInt(puzzleWidthField.getText()),
            Integer.parseInt(puzzleMinesField.getText())));
    puzzleWidthField.setOnAction(
        _ -> controller.setPuzzleParameters(
            Integer.parseInt(puzzleHeightField.getText()),
            Integer.parseInt(puzzleWidthField.getText()),
            Integer.parseInt(puzzleMinesField.getText())));
    puzzleMinesField.setOnAction(
        _ -> controller.setPuzzleParameters(
            Integer.parseInt(puzzleHeightField.getText()),
            Integer.parseInt(puzzleWidthField.getText()),
            Integer.parseInt(puzzleMinesField.getText())));
    return customPuzzlePanel;
  }
}
