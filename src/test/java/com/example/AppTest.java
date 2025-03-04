package com.example;

import static org.example.model.PuzzleGenerator.PuzzleDifficulty.*;
import static org.example.model.RenderType.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import org.example.model.*;
import org.example.model.PuzzleGenerator.PuzzleDifficulty;
import org.example.model.CellStateMap.CellState;
import org.junit.Test;

public class AppTest {
  @Test
  public void testModelCheckIndex() {
    Model model = new ModelImpl();
    boolean check1 = false;
    try {
      model.revealCell(-1, 100, true);
    } catch (IndexOutOfBoundsException ignored) {
      check1 = true;
    }
    assertTrue(check1);
  }

  @Test
  public void testModelRevealCell() {
    Model model = new ModelImpl();
    Coordinate origin = new CoordinateImpl(0, 0);
    assertEquals(CellState.HIDE, model.getCellState(origin));
    model.revealCell(0, 0, true);
    assertEquals(CellState.SHOW, model.getCellState(origin));
    model.resetPuzzle(TEST);
    //    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
    //      System.out.println(Arrays.deepToString(model.getCellStateMap()[i]));
    //    }
    model.revealCell(0, 0, true);
    //    System.out.println();
    //    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
    //      System.out.println(Arrays.deepToString(model.getCellStateMap()[i]));
    //    }
  }

  @Test
  public void testModelFlags() {
    Model model = new ModelImpl();
    Coordinate origin = new CoordinateImpl(0, 0);
    assertFalse(model.isFlag(origin));
    model.removeFlag(0, 0);
    assertFalse(model.isFlag(origin));
    model.addFlag(0, 0);
    assertTrue(model.isFlag(origin));
    model.addFlag(0, 0);
    assertTrue(model.isFlag(origin));
    model.removeFlag(0, 0);
    assertFalse(model.isFlag(origin));
    // Testing flagging a revealed cell.
    assertTrue(model.isHide(origin));
    model.revealCell(0, 0, true);
    model.addFlag(0, 0);
    assertTrue(model.isFlag(origin));
  }

  @Test
  public void testModelResetPuzzle() {
    Model model = new ModelImpl();
    assertEquals(90, model.getRevealGoal());
    int puzzleHeight = model.getPuzzle().getHeight();
    int puzzleWidth = model.getPuzzle().getWidth();
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        model.revealCell(r, c, true);
        assertTrue(model.isShow(new CoordinateImpl(r, c)));
      }
    }
    assertEquals(0, model.getRevealGoal());
    model.resetPuzzle(TEST);
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        assertTrue(model.isHide(new CoordinateImpl(r, c)));
      }
    }
    assertEquals(90, model.getRevealGoal());
  }

  @Test
  public void testPuzzleGenerator() {
    PuzzleGeneratorImpl puzzleGeneratorImpl = new PuzzleGeneratorImpl(new CoordinateImpl(4, 4), EASY);
    puzzleGeneratorImpl.generateBlankBoard();
    for (int row = 0; row < puzzleGeneratorImpl.getBoard().length; row++) {
      for (int col = 0; col < puzzleGeneratorImpl.getBoard()[0].length; col++) {
        assertEquals(0, puzzleGeneratorImpl.getBoard()[row][col]);
      }
    }
  }

  @Test
  public void testPuzzleGeneratorMinePlacement() {
    PuzzleGeneratorImpl puzzleGeneratorImpl = new PuzzleGeneratorImpl(new CoordinateImpl(0, 0), EASY);

    for (int i = 0; i < 10; i++) {
      puzzleGeneratorImpl.generateBlankBoard();
      puzzleGeneratorImpl.placeMines();
      for (int row = 0; row < puzzleGeneratorImpl.getBoard().length; row++) {
        for (int r = -1; r <= 1; r++) {
          for (int c = -1; c <= 1; c++) {
            if (r >= 0 && c >= 0) {
              assertEquals(0, puzzleGeneratorImpl.getBoard()[r][c]);
            }
          }
        }
      }
    }
  }

  @Test
  public void testPuzzleGeneratorCluePlacement() {
    PuzzleDifficulty[] difficulties = new PuzzleDifficulty[] {EASY, MEDIUM, HARD};

    for (int i = 0; i < 3; i++) {
      PuzzleGeneratorImpl puzzleGeneratorImpl =
          new PuzzleGeneratorImpl(new CoordinateImpl(4, 4), difficulties[i]);
      puzzleGeneratorImpl.generateBlankBoard();
      puzzleGeneratorImpl.placeMines();
      puzzleGeneratorImpl.placeClues();

      for (int row = 0; row < puzzleGeneratorImpl.getBoard().length; row++) {
        System.out.println((Arrays.toString(puzzleGeneratorImpl.getBoard()[row])));
      }
      System.out.println();
    }
  }
}
