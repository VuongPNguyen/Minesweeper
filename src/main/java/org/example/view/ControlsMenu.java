package org.example.view;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ControlsMenu implements FXComponent {
  @Override
  public Parent render() {
    VBox vBox = new VBox();
    vBox.setStyle("-fx-font-size: 15");
    Label title = new Label("Controls");
    title.setStyle("-fx-font-weight: bold");
    Label reveal = new Label("Left-click an empty square to reveal it.");
    Label flag = new Label("Right-click an empty square to flag it.");
    Label revealClue = new Label("Left-click a number to reveal its adjacent squares.");

    vBox.getChildren().add(title);
    vBox.getChildren().add(reveal);
    vBox.getChildren().add(flag);
    vBox.getChildren().add(revealClue);
    return vBox;
  }
}
