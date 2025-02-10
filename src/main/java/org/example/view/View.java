package org.example.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.example.controller.Controller;
import org.example.model.Model;
import org.example.model.ModelObserver;

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
    Rectangle2D screen = Screen.getPrimary().getVisualBounds();
    Pane vBox = new VBox();
    vBox.getStyleClass().add("client");
    vBox.setMaxHeight(MaxScreenHeight);
    vBox.setMaxWidth(MaxScreenWidth);

    vBox.setPrefSize(MaxScreenWidth, MaxScreenHeight);
    Mine mine = new Mine(model, 0, 0);
    vBox.getChildren().add(mine.render());
    Clue clue = new Clue(model, 4, 1);
    vBox.getChildren().add(clue.render());
    Blank blank = new Blank(model, 0, 0);
    vBox.getChildren().add(blank.render());

    return vBox;
  }
}
