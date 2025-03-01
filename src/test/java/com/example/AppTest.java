package com.example;

import static org.example.Puzzles.*;
import static org.example.model.PuzzleGenerator.PuzzleDifficulty.*;
import static org.example.model.RenderType.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import org.example.model.*;
import org.example.model.PuzzleGenerator.PuzzleDifficulty;
import org.junit.Test;

public class AppTest {
  private Puzzle puzzle = new PuzzleImpl(PUZZLE_01);

  @Test
  public void testPuzzleImplCheckIndex() {
    boolean check1 = false, check2 = false, check3 = true;
    puzzle = new PuzzleImpl(PUZZLE_01);
    try {
      puzzle.getCellType(8, 0);
    } catch (IndexOutOfBoundsException ignored) {
      check1 = true;
    } catch (Exception ignored) {
    }
    assertTrue(check1);

    puzzle = new PuzzleImpl(PUZZLE_02);
    try {
      puzzle.getCellType(0, 18);
    } catch (IndexOutOfBoundsException ignored) {
      check2 = true;
    } catch (Exception ignored) {
    }
    assertTrue(check2);

    puzzle = new PuzzleImpl(PUZZLE_03);
    try {
      puzzle.getCellType(5, 5);
    } catch (Exception ignored) {
      check3 = false;
    }
    assertTrue(check3);
  }

  @Test
  public void testPuzzleGetCellType() {
    puzzle = new PuzzleImpl(PUZZLE_01);
    assertEquals(CellType.BLANK, puzzle.getCellType(0, 0));
    assertEquals(CellType.CLUE, puzzle.getCellType(3, 1));
    assertEquals(CellType.CLUE, puzzle.getCellType(5, 1));
    assertEquals(CellType.MINE, puzzle.getCellType(1, 9));
    int[][] testBoard = {{11}};
    puzzle = new PuzzleImpl(testBoard);
    assertEquals(CellType.ERROR, puzzle.getCellType(0, 0));
  }

  @Test
  public void testPuzzleGetClue() {
    puzzle = new PuzzleImpl(PUZZLE_03);
    assertEquals(1, puzzle.getClue(0, 3));
    assertEquals(2, puzzle.getClue(1, 3));
    assertEquals(3, puzzle.getClue(0, 4));
    assertEquals(4, puzzle.getClue(2, 5));
    assertEquals(5, puzzle.getClue(7, 4));
    int[][] testBoard = {{6, 7, 8}};
    puzzle = new PuzzleImpl(testBoard);
    assertEquals(6, puzzle.getClue(0, 0));
    assertEquals(7, puzzle.getClue(0, 1));
    assertEquals(8, puzzle.getClue(0, 2));
  }

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
    assertEquals(CellState.HIDE, model.getCellState(0, 0));
    model.revealCell(0, 0, true);
    assertEquals(CellState.SHOW, model.getCellState(0, 0));
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
    assertFalse(model.isFlag(0, 0));
    model.removeFlag(0, 0);
    assertFalse(model.isFlag(0, 0));
    model.addFlag(0, 0);
    assertTrue(model.isFlag(0, 0));
    model.addFlag(0, 0);
    assertTrue(model.isFlag(0, 0));
    model.removeFlag(0, 0);
    assertFalse(model.isFlag(0, 0));
    // Testing flagging a revealed cell.
    assertEquals(CellState.HIDE, model.getCellState(0, 0));
    model.revealCell(0, 0, true);
    model.addFlag(0, 0);
    assertEquals(CellState.SHOW, model.getCellState(0, 0));
  }

  @Test
  public void testModelResetPuzzle() {
    Model model = new ModelImpl();
    assertEquals(90, model.getRevealGoal());
    int puzzleHeight = model.getActivePuzzle().getHeight();
    int puzzleWidth = model.getActivePuzzle().getWidth();
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        model.revealCell(r, c, true);
        assertEquals(CellState.SHOW, model.getCellState(r, c));
      }
    }
    assertEquals(0, model.getRevealGoal());
    model.resetPuzzle(TEST);
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        assertEquals(CellState.HIDE, model.getCellState(r, c));
      }
    }
    assertEquals(90, model.getRevealGoal());
  }

  @Test
  public void testModelIsSolved() {
    Model model = new ModelImpl();
    int puzzleHeight = model.getActivePuzzle().getHeight();
    int puzzleWidth = model.getActivePuzzle().getWidth();
    for (int r = 0; r < puzzleHeight; r++) {
      for (int c = 0; c < puzzleWidth; c++) {
        if (PUZZLE_01[r][c] != 9) {
          model.revealCell(r, c, true);
        }
      }
    }
    assertEquals(GameState.WIN, model.getGameState());
  }

  @Test
  public void testPuzzleGenerator() {
    PuzzleGeneratorImpl puzzleGeneratorImpl = new PuzzleGeneratorImpl(new int[] {4, 4}, EASY);
    puzzleGeneratorImpl.generateBlankBoard();
    for (int row = 0; row < puzzleGeneratorImpl.getBoard().length; row++) {
      for (int col = 0; col < puzzleGeneratorImpl.getBoard()[0].length; col++) {
        assertEquals(0, puzzleGeneratorImpl.getBoard()[row][col]);
      }
    }
  }

  @Test
  public void testPuzzleGeneratorMinePlacement() {
    PuzzleGeneratorImpl puzzleGeneratorImpl = new PuzzleGeneratorImpl(new int[] {0, 0}, EASY);

    for (int i = 0; i < 10; i++) {
      puzzleGeneratorImpl.generateBlankBoard();
      puzzleGeneratorImpl.placeMines();
      for (int row = 0; row < puzzleGeneratorImpl.getBoard().length; row++) {
        // System.out.println((Arrays.toString(puzzleGeneratorImpl.getBoard()[row])));
        for (int r = -1; r <= 1; r++) {
          for (int c = -1; c <= 1; c++) {
            if (r >= 0 && c >= 0) {
              assertEquals(0, puzzleGeneratorImpl.getBoard()[r][c]);
            }
          }
        }
      }
      // System.out.println();
    }
  }

  @Test
  public void testPuzzleGeneratorCluePlacement() {
    PuzzleDifficulty[] difficulties = new PuzzleDifficulty[] {EASY, MEDIUM, HARD};

    for (int i = 0; i < 3; i++) {
      PuzzleGeneratorImpl puzzleGeneratorImpl =
          new PuzzleGeneratorImpl(new int[] {4, 4}, difficulties[i]);
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
