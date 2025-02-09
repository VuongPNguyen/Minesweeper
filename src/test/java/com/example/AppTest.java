package com.example;

import static org.example.Puzzles.*;
import static org.junit.Assert.*;

import org.example.model.*;
import org.junit.Test;

import java.sql.Array;

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
    } catch (Exception ignored) {}
    assertTrue(check1);
    
    puzzle = new PuzzleImpl(PUZZLE_02);
    try {
      puzzle.getCellType(0, 18);
    } catch (IndexOutOfBoundsException ignored) {
      check2 = true;
    } catch (Exception ignored) {}
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
}
