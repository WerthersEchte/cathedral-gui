module cathedral.gui {
  requires javafx.controls;
  requires javafx.fxml;
  requires cathedral.game;

  opens de.fhkiel.ki.ui to javafx.fxml;
  exports de.fhkiel.ki.ui;
  exports de.fhkiel.ki.ai;
}