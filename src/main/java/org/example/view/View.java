package org.example.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.controller.Controller;
import org.example.model.Model;
import org.example.model.ModelObserver;

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
    Pane stackPane = new StackPane();
    
    Pane vBox = new VBox();

    // vBox.getChildren().add(new ImageView(new Image("mine.png")));

    stackPane.getChildren().add(vBox);
    return stackPane;
  }
}
