package org.example.view;

import static org.example.view.ViewConstants.*;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import org.example.model.CellType;
import org.example.model.Model;
import org.example.model.Puzzle;

public class PuzzleGrid implements FXComponent {
  private final Model model;

  public PuzzleGrid(Model model) {
    this.model = model;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    grid.getStyleClass().add("grid");
    grid.setMaxHeight(MaxScreenHeight);
    
    Puzzle activePuzzle = model.getActivePuzzle();
    for (int row = 0; row < activePuzzle.getHeight(); row++) {
      for (int col = 0; col < activePuzzle.getWidth(); col++) {
        if (activePuzzle.getCellType(row, col) == CellType.BLANK) {
          Blank blank = new Blank(model);
          grid.add(blank.render(), col, row);
        } else if (activePuzzle.getCellType(row, col) == CellType.CLUE) {
          Clue clue = new Clue(model, row, col);
          grid.add(clue.render(), col, row);
        } else if (activePuzzle.getCellType(row, col) == CellType.MINE) {
          Mine mine = new Mine(model, row, col);
          grid.add(mine.render(), col, row);
        }
      }
    }
    grid.setHgap(gridGap);
    grid.setVgap(gridGap);
    grid.setGridLinesVisible(true);

    return grid;
  }
}
