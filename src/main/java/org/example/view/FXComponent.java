package org.example.view;

import javafx.scene.Parent;

public interface FXComponent {
  /** Render the component and return the resulting Parent object */
  Parent render();
}