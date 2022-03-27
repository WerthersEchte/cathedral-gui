package de.fhkiel.ki.ai;

import de.fhkiel.ki.cathedral.Game;
import de.fhkiel.ki.cathedral.Placement;

public interface CathedralAI {
  default String name(){
    return getClass().getName();
  }
  void init(Game game);
  Placement takeTurn(Game game);
  void stopAI();
}
