package de.fhkiel.ki.ui;

import de.fhkiel.ki.ai.CathedralAI;
import de.fhkiel.ki.cathedral.Board;
import de.fhkiel.ki.cathedral.Building;
import de.fhkiel.ki.cathedral.Color;
import de.fhkiel.ki.cathedral.Game;
import de.fhkiel.ki.cathedral.Placement;
import de.fhkiel.ki.cathedral.Turn;
import de.fhkiel.ki.ui.game.GameListener;
import de.fhkiel.ki.ui.game.GameWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;

public class Base {

  private GameWrapper game;

  @FXML
  private Field fieldController;
  @FXML
  private BuildingList buildingListController;
  @FXML
  private Control controlController;

  public void initialize() {
    game = new GameWrapper(new Game());

    game.listen(fieldController);
    game.listen(buildingListController);
    game.listen(controlController);

    game.init();

  }

  public void addAI(CathedralAI... ai) {
    controlController.addAI(ai);
  }

  public void close(ActionEvent actionEvent) {
    Platform.exit();
  }
}
