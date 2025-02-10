package org.example.view;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.example.controller.Controller;
import org.example.model.CellState;
import org.example.model.CellType;
import org.example.model.Model;
import org.example.model.Puzzle;

import static org.example.view.ViewConstants.gridGap;

public class PlayGrid implements FXComponent {
  private final Model model;
  private final Controller controller;

  public PlayGrid(Model model, Controller controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    grid.getStyleClass().add("play-grid");

    Puzzle activePuzzle = model.getActivePuzzle();

    for(int row = 0; row < activePuzzle.getHeight(); row++) {
      for (int col = 0; col < activePuzzle.getWidth(); col++) {
        if (model.getCellState(row, col) == CellState.HIDE) {
          Hide hide = new Hide(model, controller, row, col);
          grid.add(hide.render(), col, row);
        } else if (model.getCellState(row, col) == CellState.FLAG) {
          Flag flag = new Flag(model, controller, row, col);
          grid.add(flag.render(), col, row);
        } else if (model.getCellState(row, col) == CellState.SHOW) {
          Show show = new Show(model, controller, row, col);
          grid.add(show.render(), col, row);
        }
      }
    }
    grid.setHgap(gridGap);
    grid.setVgap(gridGap);

    return grid;
  }
}
