package org.example.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.controller.*;
import org.example.model.*;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    Model model = new ModelImpl();
    Controller controller = new ControllerImpl(model);
    View view = new View(model, controller, stage);

    model.addObserver(view);
    Scene scene = new Scene(view.render(RenderType.NEW_PUZZLE));
    scene.getStylesheets().add("main.css");

    stage.setScene(scene);
    stage.getIcons().add(new Image("mine.png"));
    stage.setTitle("Minesweeper");

    stage.show();
    stage.setX(stage.getX() - 1.5);
    stage.setMaximized(true);
  }
}
