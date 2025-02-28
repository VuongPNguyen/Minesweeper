package org.example.view;

import static org.example.view.ViewConstants.MaxScreenHeight;
import static org.example.view.ViewConstants.gridGap;

import java.util.Arrays;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.example.model.CellType;
import org.example.model.Model;
import org.example.model.Puzzle;

public class PlayGridRevealMines implements FXComponent {
  private final Model model;

  public PlayGridRevealMines(Model model) {
    this.model = model;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    grid.getStyleClass().add("play-grid");
    grid.setMaxHeight(MaxScreenHeight);

    Puzzle activePuzzle = model.getActivePuzzle();
    EmptyCell emptyCell = new EmptyCell(model);
    Mine mine = new Mine(model);
    ExplodedMine explodedMine = new ExplodedMine(model);
    WrongFlag wrongFlag = new WrongFlag(model);

    for (int row = 0; row < activePuzzle.getHeight(); row++) {
      for (int col = 0; col < activePuzzle.getWidth(); col++) {
        if (activePuzzle.getCellType(row, col) == CellType.MINE) {
          if (Arrays.equals(model.getExplodedMine(), new int[] {row, col})) {
            grid.add(explodedMine.render(), col, row);
          } else if (!model.isFlag(row, col)) {
            grid.add(mine.render(), col, row);
          }
        } else if (model.isFlag(row, col) && !model.isMine(row, col)) {
          grid.add(wrongFlag.render(), col, row);
        } else {
          grid.add(emptyCell.render(), col, row);
        }
      }
    }

    grid.setHgap(gridGap);
    grid.setVgap(gridGap);
    grid.setGridLinesVisible(true);

    return grid;
  }
}
