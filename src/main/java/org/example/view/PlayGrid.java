package org.example.view;

import static org.example.view.ViewConstants.*;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.example.controller.Controller;
import org.example.model.*;

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

    Puzzle activePuzzle = model.getPuzzle();
    Mine mine = new Mine(model);
    ExplodedMine explodedMine = new ExplodedMine(model);
    WrongFlag wrongFlag = new WrongFlag(model);

    for (int row = 0; row < activePuzzle.getHeight(); row++) {
      for (int col = 0; col < activePuzzle.getWidth(); col++) {
        Coordinate coord = new CoordinateImpl(row, col);
        if (model.isHide(coord)) {
          Hide hide = new Hide(model, controller, coord);
          grid.add(hide.render(), coord.col(), coord.row());
        } else if (model.isFlag(coord)) {
          if (model.getGameState() == GameState.LOSE
              && !model.isMine(coord)
              && model.isFlag(coord)) {
            grid.add(wrongFlag.render(), coord.col(), coord.row());
          } else {
            Flag flag = new Flag(model, controller, coord);
            grid.add(flag.render(), coord.col(), coord.row());
          }
        } else if (model.isShow(coord)) {
          if (model.isMine(coord)) {
            Coordinate trippedMine = model.getExplodedMine();
            if (trippedMine.row() == row && trippedMine.col() == col) {
              grid.add(explodedMine.render(), coord.col(), coord.row());
            } else {
              grid.add(mine.render(), coord.col(), coord.row());
            }
          } else if (model.isBlank(coord)) {
            Blank blank = new Blank(model);
            grid.add(blank.render(), coord.col(), coord.row());
          } else if (model.isClue(coord)) {
            Clue clue = new Clue(model, controller, coord);
            grid.add(clue.render(), coord.col(), coord.row());
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
