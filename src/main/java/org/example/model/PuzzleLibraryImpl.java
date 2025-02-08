package org.example.model;

import java.util.ArrayList;

public class PuzzleLibraryImpl implements PuzzleLibrary {
  ArrayList<Puzzle> puzzleList;
  
  public PuzzleLibraryImpl() {
    this.puzzleList = new ArrayList<>();
  }
  
  @Override
  public void addPuzzle(Puzzle puzzle) {
    if (puzzle == null) {
      throw new IllegalArgumentException("Puzzle is null");
    }
    puzzleList.add(puzzle);
  }
  
  @Override
  public Puzzle getPuzzle(int index) {
    if (index >= this.size()) {
      throw new IndexOutOfBoundsException("Index is out of bounds of PuzzleList");
    }
    return puzzleList.get(index);
  }
  
  @Override
  public int size() {
    return puzzleList.size();
  }
}
