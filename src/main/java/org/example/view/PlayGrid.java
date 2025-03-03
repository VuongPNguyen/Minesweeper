package org.example.view;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.example.controller.Controller;
import org.example.model.*;

import static org.example.view.ViewConstants.*;

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
    grid.setMaxHeight(MaxScreenHeight);
    grid.setMaxWidth(MaxScreenWidth - 50);

    Puzzle activePuzzle = model.getActivePuzzle();
    Mine mine = new Mine(model);
    ExplodedMine explodedMine = new ExplodedMine(model);
    WrongFlag wrongFlag = new WrongFlag(model);
    
    for (int row = 0; row < activePuzzle.getHeight(); row++) {
      for (int col = 0; col < activePuzzle.getWidth(); col++) {
        if (model.getCellState(row, col) == CellState.HIDE) {
          Hide hide = new Hide(model, controller, row, col);
          grid.add(hide.render(), col, row);
        } else if (model.getCellState(row, col) == CellState.FLAG) {
          if (model.getGameState() == GameState.LOSE && !model.isMine(row, col) && model.isFlag(row, col)) {
            grid.add(wrongFlag.render(), col, row);
          } else {
            Flag flag = new Flag(model, controller, row, col);
            grid.add(flag.render(), col, row);
          }
        } else if (model.getCellState(row, col) == CellState.SHOW) {
          if (activePuzzle.getCellType(row, col) == CellType.MINE) {
            Coordinate trippedMine = model.getExplodedMine();
            if (trippedMine.row() == row && trippedMine.col() == col) {
              grid.add(explodedMine.render(), col, row);
            } else {
              grid.add(mine.render(), col, row);
            }
          } else if (activePuzzle.getCellType(row, col) == CellType.BLANK) {
            Blank blank = new Blank(model);
            grid.add(blank.render(), col, row);
          } else if (activePuzzle.getCellType(row, col) == CellType.CLUE) {
            Clue clue = new Clue(model, controller, row, col);
            grid.add(clue.render(), col, row);
          }
        }
      }
    }
    grid.setHgap(gridGap);
    grid.setVgap(gridGap);
    grid.setGridLinesVisible(true);

    return grid;
  }
}
