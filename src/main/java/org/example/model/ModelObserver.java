package org.example.model;

public interface ModelObserver {
  /**
   * When a model value is changed, the model calls update() with a unique renderType on all active
   * ModelObserver objects
   */
  void update(Model model, RenderType renderType);
}
