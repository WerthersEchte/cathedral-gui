package de.fhkiel.ki.ui;

import de.fhkiel.ki.cathedral.Building;
import de.fhkiel.ki.cathedral.Direction;
import de.fhkiel.ki.cathedral.Game;
import de.fhkiel.ki.cathedral.Placement;
import de.fhkiel.ki.cathedral.Position;
import de.fhkiel.ki.ui.game.GameListener;
import de.fhkiel.ki.ui.game.GameWrapper;
import java.util.Arrays;
import java.util.Optional;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Field implements GameListener {
  public GridPane grid;

  private Pane[][] field = new Pane[10][10];

  public void initialize() {
    System.out.println(grid.getStyleClass().stream().reduce("G: ", String::concat));

    for (int y = 0; y <= 9; ++y){
      for (int x = 0; x <= 9; ++x){
        Pane position = new Pane();
        position.setMinSize(40.0, 40.0);
        position.setPrefSize(40.0, 40.0);
        position.setMaxSize(40.0, 40.0);

        Label id = new Label();
        id.setMinSize(40.0, 40.0);
        id.setPrefSize(40.0, 40.0);
        id.setMaxSize(40.0, 40.0);
        id.setAlignment(Pos.CENTER);
        id.setText("");
        position.getChildren().add(id);

        if((x+y)%2 == 0) {
          position.getStyleClass().add("lgray");
        } else {
          position.getStyleClass().add("dgray");
        }

        position.setOnDragOver(new EventHandler<DragEvent>() {
          public void handle(DragEvent event) {
            if (event.getGestureSource() != position && event.getDragboard().hasString()) {
              event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
          }
        });

        Position p = new Position(x, y);
        position.setOnDragDropped((DragEvent event) -> {
          Dragboard db = event.getDragboard();
          if (event.getDragboard().hasString()) {
            System.out.println("Dropped: " + db.getString());

            Optional<Building>
                building = Arrays.stream(Building.values()).filter(b -> Integer.parseInt(event.getDragboard().getString().split(";")[0]) == b.getId()).findAny();
            Direction direction = Direction.values()[Integer.parseInt(event.getDragboard().getString().split(";")[1])];
            if(building.isPresent()){
              System.out.println("Fire a Turn: " + p + "-" + direction + "-" + building.get() + " -> " + game.takeTurn(new Placement(p, direction, building.get())));

            }
            event.setDropCompleted(true);
          } else {
            event.setDropCompleted(false);
          }
          event.consume();
        });

        grid.add(position, x, y);
        field[y][x] = position;
      }
    }
  }

  private GameWrapper game;

  @Override
  public void initGame(GameWrapper game) {
    this.game = game;
    fillField(game);
  }

  @Override
  public void actionTaken(GameWrapper game) {
    fillField(game);
  }

  private void fillField(GameWrapper game) {
    for (int y = 0; y <= 9; ++y) {
      for (int x = 0; x <= 9; ++x) {
        ((Label) field[y][x].getChildren().get(0)).setText("");

        field[y][x].getStyleClass().clear();
        switch(game.getBoard().getField()[y][x]){
          case Blue -> field[y][x].getStyleClass().add("fBlue");
          case Black -> field[y][x].getStyleClass().add("fBlack");
          case White -> field[y][x].getStyleClass().add("fWhite");
          case Black_Owned -> field[y][x].getStyleClass().add("fBlackOwned");
          case White_Owned -> field[y][x].getStyleClass().add("fWhiteOwned");
          case None -> {
            if((x+y)%2 == 0) {
              field[y][x].getStyleClass().add("lgray");
            } else {
              field[y][x].getStyleClass().add("dgray");
            }
          }
        }
      }
    }
    for(Placement placement : game.getBoard().getPlacedBuildings()){
      for (Position p : placement.form()){
        Label id = ((Label) field[p.y()+placement.y()][p.x()+ placement.x()].getChildren().get(0));
        id.setText("" + placement.building().getId());

        id.getStyleClass().clear();
        switch(placement.building().getColor()){
          case Blue -> id.getStyleClass().add("wText");
          case White -> id.getStyleClass().add("bText");
          case Black -> id.getStyleClass().add("wText");
        }
      }
    }
  }

}
