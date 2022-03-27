package de.fhkiel.ki.ui;

import de.fhkiel.ki.ai.CathedralAI;
import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CathedralGUI extends javafx.application.Application {

  private static CathedralGUI instance;

  public CathedralGUI() {
    instance = this;
  }

  private static CathedralGUI getInstance() {
    if (instance == null) {
      new Thread(() -> Application.launch(CathedralGUI.class)).start();
      while (instance == null || instance.base == null) {
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
    return instance;
  }

  public static void launchGUI(CathedralAI... ai){
    getInstance().base.addAI(ai);
  }

  Base base;

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("base.fxml"));
    Parent root = loader.load();
    base = loader.getController();

    Scene scene = new Scene(root);
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles/base.css")).toExternalForm());

    stage.setTitle("JavaFX and Gradle");
    stage.setScene(scene);
    stage.show();
  }
}