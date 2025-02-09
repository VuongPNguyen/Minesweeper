package org.example.view;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class ViewConstants {
  public static final Rectangle2D screen = Screen.getPrimary().getVisualBounds();
  public static double MaxScreenHeight = screen.getHeight() - 32;
  public static double MaxScreenWidth = screen.getWidth() - 2;
}
