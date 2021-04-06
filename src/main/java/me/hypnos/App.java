package me.hypnos;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.hypnos.Controls.KeyEvent;
import me.hypnos.Controls.GameMechanics;
import me.hypnos.Graphics.Drawer;
import me.hypnos.view.ViewManager;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;


public class App extends Application {

    private GraphicsContext gc;
    private Timeline timeline;


    Drawer drawer;
    GameMechanics mechanics;


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