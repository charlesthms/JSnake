package me.hypnos;

import javafx.application.Application;
import javafx.stage.Stage;
import me.hypnos.view.ViewManager;


public class App extends Application {


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        mainMenuInit(stage);
    }

    private void mainMenuInit(Stage stage){
        ViewManager manager = new ViewManager();
        stage = manager.getMainStage();
        stage.show();
    }
}