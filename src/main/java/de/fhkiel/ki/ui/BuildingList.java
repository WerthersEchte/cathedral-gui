package de.fhkiel.ki.ui;

import de.fhkiel.ki.cathedral.Building;
import de.fhkiel.ki.cathedral.Direction;
import de.fhkiel.ki.cathedral.Position;
import de.fhkiel.ki.ui.game.GameListener;
import de.fhkiel.ki.ui.game.GameWrapper;
import java.util.EnumMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class BuildingList implements GameListener {

  public FlowPane list;

  private Map<Building, Container> buildings = new EnumMap<Building, Container>(Building.class);

  public void initialize() {

    for(Building building : Building.values()){
      GridPane base = new GridPane();

      base.getColumnConstraints().addAll(new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints(), new ColumnConstraints());
      base.getRowConstraints().addAll(new RowConstraints(), new RowConstraints(), new RowConstraints(), new RowConstraints(), new RowConstraints());

      base.setOnDragDetected((MouseEvent event) -> {

        Dragboard db = base.startDragAndDrop(TransferMode.MOVE);
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);
        db.setDragView(base.snapshot(sp, null));
        db.setDragViewOffsetX(event.getX());
        db.setDragViewOffsetY(event.getY());

        ClipboardContent content = new ClipboardContent();
        content.putString("" + building.getId() + ";" + buildings.get(building).direction.getId());
        db.setContent(content);

        base.setVisible(false);
      });
      base.setOnMouseDragged((MouseEvent event) -> {
        event.setDragDetect(true);
      });
      base.setOnDragDone(event -> base.setVisible(true));

      base.setOnMouseClicked(event -> {
        buildings.get(building).direction = Direction.values()[(buildings.get(building).direction.getId() + 1) % building.getTurnable().getNumberOfPossibleForms()];
        buildings.get(building).repaint();
      });

      Container container = new Container(base, building);
      container.repaint();

      list.getChildren().add(base);
      buildings.put(building, container);
    }

  }

  private GameWrapper game;

  @Override
  public void initGame(GameWrapper game) {
    this.game = game;
    checkPlaceableBuildings(game);
  }

  @Override
  public void actionTaken(GameWrapper game) {
    checkPlaceableBuildings(game);
  }

  private void checkPlaceableBuildings(GameWrapper game) {
    for (Building building : buildings.keySet()){
      if(!game.getAllBuildings().contains(building)){
        buildings.get(building).base.setVisible(false);
        list.getChildren().remove(buildings.get(building).base);
      } else if(!list.getChildren().contains(buildings.get(building).base)){
        list.getChildren().add(buildings.get(building).base);
        buildings.get(building).base.setVisible(true);
        }
    }
  }

  private static class Container{
    Direction direction = Direction._0;
    Building building;
    GridPane base;

    public Container(GridPane base, Building building) {
      this.base = base;
      this.building = building;
    }

    public void repaint() {

      base.getChildren().clear();
      for(Position p : building.turn(direction)){
        Pane part = new Pane();
        part.setMinSize(40.0, 40.0);
        part.setPrefSize(40.0, 40.0);
        part.setMaxSize(40.0, 40.0);

        switch(building.getColor()){
          case Black -> part.getStyleClass().add("blackBuilding");
          case White -> part.getStyleClass().add("whiteBuilding");
          case Blue -> part.getStyleClass().add("blueBuilding");
        }
        if(p.x() == 0 && p.y() == 0){
          part.getStyleClass().add("center");
        }

        Label id = new Label();
        id.setMinSize(40.0, 40.0);
        id.setPrefSize(40.0, 40.0);
        id.setMaxSize(40.0, 40.0);
        id.setAlignment(Pos.CENTER);
        id.setText(building.getId() + "");
        switch(building.getColor()){
          case Blue -> id.getStyleClass().add("wText");
          case White -> id.getStyleClass().add("bText");
          case Black -> id.getStyleClass().add("wText");
        }
        part.getChildren().add(id);

        base.add(part, p.x()+3, p.y()+3);
      }
    }
  }
}
