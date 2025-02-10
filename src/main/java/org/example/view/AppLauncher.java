package org.example.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.controller.*;
import org.example.model.*;

import static org.example.Puzzles.*;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibrary puzzleLibrary = new PuzzleLibraryImpl();
    puzzleLibrary.addPuzzle(new PuzzleImpl(PUZZLE_01));
    puzzleLibrary.addPuzzle(new PuzzleImpl(PUZZLE_02));
    puzzleLibrary.addPuzzle(new PuzzleImpl(PUZZLE_03));

    Model model = new ModelImpl(puzzleLibrary);
    model.setActivePuzzleIndex(0);
    Controller controller = new ControllerImpl(model);
    View view = new View(model, controller, stage);

    model.addObserver(view);
    Scene scene = new Scene(view.render());
    scene.getStylesheets().add("main.css");

    stage.setScene(scene);
    stage.getIcons().add(new Image("mine.png"));
    stage.setTitle("Minesweeper");

    stage.show();
    stage.setY(stage.getY() + 2);
  }
}
