package de.fhkiel.ki.ui;

import de.fhkiel.ki.ai.CathedralAI;
import de.fhkiel.ki.cathedral.Color;
import de.fhkiel.ki.ui.game.GameListener;
import de.fhkiel.ki.ui.game.GameWrapper;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

public class Control implements GameListener {

  public ChoiceBox<String> selectWhite;
  public ChoiceBox<String> selectBlack;

  public Button ttBlack;
  public Button ttWhite;

  public Label scoreWhite;
  public Label scoreBlack;

  public TextField nameBlack;
  public TextField nameWhite;

  public VBox white;
  public VBox black;

  private Map<String, CathedralAI> ais = new HashMap<>();
  private CathedralAI currentWhiteAI = null;
  private CathedralAI currentBlackAI = null;

  public void initialize() {
    selectWhite.setOnAction((event) -> {
      if (ais.containsKey(selectWhite.getValue())) {
        currentWhiteAI = ais.get(selectWhite.getValue());
        currentWhiteAI.init(game.getGame());
        ttWhite.setDisable(false);
        nameWhite.setText(selectWhite.getValue());
      } else {
        currentWhiteAI = null;
        ttWhite.setDisable(true);
      }
    });
    selectBlack.setOnAction((event) -> {
      if (ais.containsKey(selectBlack.getValue())) {
        currentBlackAI = ais.get(selectBlack.getValue());
        currentBlackAI.init(game.getGame());
        ttBlack.setDisable(false);
        nameBlack.setText(selectBlack.getValue());
      } else {
        currentBlackAI = null;
        ttBlack.setDisable(true);
      }
    });
  }

  public void addAI(CathedralAI... ai) {
    ObservableList<String> list = FXCollections.observableArrayList("Player");

    for (CathedralAI aAi : ai) {
      ais.put(aAi.name(), aAi);
      list.add(aAi.name());
    }

    selectWhite.setItems(list);
    selectWhite.setValue("Player");
    selectBlack.setItems(list);
    selectBlack.setValue("Player");
  }

  public void rulesOff(ActionEvent actionEvent) {
    game.ignoreRules(((ToggleButton) actionEvent.getSource()).isSelected());
  }

  public void undoTurn(ActionEvent actionEvent) {
    game.undoLastTurn();
  }

  public void forfeitTurn(ActionEvent actionEvent) {
    game.forfeitTurn();
  }

  public void takeTurnWhite(ActionEvent actionEvent) {
    if(currentWhiteAI != null && (game.getCurrentPlayer() == Color.White || game.getCurrentPlayer() == Color.Blue)){
      game.takeTurn(currentWhiteAI.takeTurn(game.getGame()));
    }
  }

  public void takeTurnBlack(ActionEvent actionEvent) {
    if(currentBlackAI != null && game.getCurrentPlayer() == Color.Black){
      game.takeTurn(currentBlackAI.takeTurn(game.getGame()));
    }
  }

  GameWrapper game;

  @Override
  public void initGame(GameWrapper game) {
    this.game = game;

    currentPlayer(game);
    setScore(game);
  }

  @Override
  public void actionTaken(GameWrapper game) {

    currentPlayer(game);
    setScore(game);
  }

  private void currentPlayer(GameWrapper game) {
    white.getStyleClass().clear();
    black.getStyleClass().clear();
    switch (game.getCurrentPlayer()) {
      case Black -> black.getStyleClass().add("onThePlay");
      case White, Blue -> white.getStyleClass().add("onThePlay");
    }
  }

  private void setScore(GameWrapper game) {
    scoreBlack.setText(game.score().get(Color.Black) + "");
    scoreWhite.setText(game.score().get(Color.White) + "");
  }
}
