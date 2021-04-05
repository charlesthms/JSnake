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
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import me.hypnos.Controls.KeyEvent;
import me.hypnos.Controls.GameMechanics;
import me.hypnos.Graphics.Drawer;


import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;


public class App extends Application {

    private GraphicsContext gc;
    private Timeline timeline;


    Drawer drawer;
    GameMechanics mechanics;

    // Constantes
    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH;
    public static final int ROWS = 40;
    public static final int COLS = ROWS;
    public static final int sqrSize = WIDTH / ROWS;

    // Directions
    public static final Point vectUp = new Point(0, -1);
    public static final Point vectDown = new Point(0, 1);
    public static final Point vectRight = new Point(1, 0);
    public static final Point vectLeft = new Point(-1, 0);

    // Serpent & Direction
    public static List<Point> snakeBody = new ArrayList<>();
    public static Point snakeHead;
    public static Point currentDirection;

    // Mode de jeu
    public static boolean bordersOpen = true;
    public static int score = 0;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        // Classes de gestion du jeu
        drawer = new Drawer();
        mechanics = new GameMechanics();

        initWindow(stage);

        mechanics.initSnake();
        currentDirection = new Point(1, 0);
        GameMechanics.newFood();

        run(gc);

        // Timer du jeu
        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> run(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {

        drawer.drawBackground(gc);
        drawer.drawScore(gc);

        mechanics.move();

        drawer.drawFood(gc);
        drawer.drawSnake(gc);

        GameMechanics.checkFoodEat();
        GameMechanics.checkBorders();
        // Si le jeu est perdant
        if (mechanics.isGameOver()) timeline.stop();
    }

    private void initWindow(Stage stage){
        // Paramétrage de la fenêtre
        stage.setTitle("JSnake");
        stage.getIcons().add(new Image("file:src/main/java/img/logo.png"));
        stage.setResizable(false);

        Group root = new Group();

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        gc = canvas.getGraphicsContext2D();

        // Gestion des touches
        scene.setOnKeyPressed(new KeyEvent());
    }

}