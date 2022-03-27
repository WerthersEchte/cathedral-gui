package de.fhkiel.ki.ui.game;

import de.fhkiel.ki.cathedral.Board;
import de.fhkiel.ki.cathedral.Building;
import de.fhkiel.ki.cathedral.Color;
import de.fhkiel.ki.cathedral.Game;
import de.fhkiel.ki.cathedral.Placement;
import de.fhkiel.ki.cathedral.Turn;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameWrapper{

  private Game game;

  private List<GameListener> listeners = new ArrayList<>();
  public void listen(GameListener gameListener){
    listeners.add(gameListener);
  }

  public GameWrapper(Game game) {
    this.game = game;
  }

  public void init() {
    for (GameListener listener: listeners){
      listener.initGame(this);
    }
  }

  public boolean takeTurn(Placement placement) {
    boolean worked = game.takeTurn(placement);
    if(worked){
      for (GameListener listener: listeners){
        listener.actionTaken(this);
      }
    }
    return worked;
  }

  public Turn lastTurn() {
    return game.lastTurn();
  }

  public void undoLastTurn() {
    game.undoLastTurn();
    for (GameListener listener: listeners){
      listener.actionTaken(this);
    }
  }

  public void forfeitTurn() {
    game.forfeitTurn();
    for (GameListener listener: listeners){
      listener.actionTaken(this);
    }
  }

  public Color getCurrentPlayer() {
    return game.getCurrentPlayer();
  }

  public List<Building> getPlacableBuildings() {
    return game.getPlacableBuildings();
  }

  public List<Building> getPlacableBuildings(Color player) {
    return game.getPlacableBuildings(player);
  }

  public List<Building> getAllBuildings() {
    return game.getAllBuildings();
  }

  public Board getBoard() {
    return game.getBoard();
  }

  public Map<Color, Integer> score() {
    return game.score();
  }

  public void ignoreRules(boolean ignoreRules) {
    game.ignoreRules(ignoreRules);
  }

  public Game getGame() {
    return game;
  }
}