package me.hypnos.Core;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.hypnos.Graphics.Drawer;
import me.hypnos.view.ViewManager;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMechanics {

    private Drawer drawer;
    private Timeline timeline;
    private Stage stage;
    private Stopwatch stopwatch;

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
    public static boolean bordersOpen = false;
    public static boolean enableDarkMode = true;
    public static boolean timedMode = false;

    // Données de jeu
    public static int score = 0;
    public static int speed = 100;
    public static int time = 30;


    public GameMechanics(GraphicsContext gc, Stage gameStage) {
        // Initialisation
        drawer = new Drawer();
        stage = gameStage;

        if (timedMode) {
            stopwatch = new Stopwatch();
            stopwatch.start();
        }

        // Démarrage d'un nouveau jeu
        resetGameState();
        initSnake();
        newFood();
        drawElements(gc);
        startTimer(gc);
    }

    // ################################################ UTILITAIRES ################################################# //

    private void startTimer(GraphicsContext gc) {
        // Timer du jeu
        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> updateGameStatus(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Initialise le serpent à une taille de <b>3</b>.
     */
    private void initSnake() {
        int startingLength = 3;
        for (int i = 0; i < startingLength; i++) {
            snakeBody.add(new Point(5, 5));
        }
        snakeHead = snakeBody.get(0);
    }

    private boolean isGameOver() {
        for (int i = 1; i < snakeBody.size(); i++) {
            // Si la tête touche le corp
            if (pointsAreEquals(snakeHead, snakeBody.get(i))) return true;
        }
        // Si la tête dépasse une bordure (uniquement en mode avec bordures)
        return checkBorders();
    }

    private boolean isGameWon() {
        return snakeBody.size() == COLS * ROWS;
    }

    private boolean checkBorders() {
        if (bordersOpen && timedMode) {
            if (time - stopwatch.getElapsedSeconds() > 0){
                if (snakeHead.x > COLS) snakeHead.x = 0;
                if (snakeHead.y > ROWS) snakeHead.y = 0;
                if (snakeHead.x < 0) snakeHead.x = ROWS;
                if (snakeHead.y < 0) snakeHead.y = COLS;
                return false;
            }
        }
        if (timedMode && time - stopwatch.getElapsedSeconds() <= 0){
            return true;
        } else {
            if (snakeHead.x > COLS || snakeHead.y > ROWS) return true;
            return snakeHead.x < 0 || snakeHead.y < 0;
        }
    }

    private void resetGameState() {
        score = 0;
        snakeBody = new ArrayList<>();
        currentDirection = new Point(1, 0);
    }

    public void updateGameStatus(GraphicsContext gc) {

        drawElements(gc);
        checkFoodEat();
        if (timedMode) checkTimerEat();
        isGameOver();

        // Si le jeu est perdant
        if (isGameOver()) {
            Media gameOver = new Media(getClass().getResource("/sounds/game_over.wav").toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(gameOver);

            bordersOpen = false;
            enableDarkMode = true;

            mediaPlayer.play();
            timeline.stop();

            if (timedMode){
                stopwatch.stop();
            }

            stage.hide();

            ViewManager manager = new ViewManager();
            stage = manager.getMainStage();
            stage.show();
        } else if (isGameWon()) {
            // Fin du jeu et victoire
            timeline.stop();
        }
    }

    // ########################################## GESTION DU SERPENT + FOOD ######################################### //

    private void drawElements(GraphicsContext gc) {
        drawer.drawBackground(gc);
        drawer.drawScore(gc);
        if (timedMode) drawer.drawTimer(gc, time - stopwatch.getElapsedSeconds());
        move();
        drawer.drawApple(gc);
        if (timedMode) drawer.drawTimer(gc);
        drawer.drawSnake(gc);
    }

    private void move() {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        addDirectionToHead();
    }

    /**
     * Génère une pomme aléatoirement sur le plateau
     */
    private void newFood() {
        boolean gen = true;
        Point p = generateNewPoint();

        while (gen) {
            // Vérification si le point généré n'est pas dans le serpent
            for (Point snake : snakeBody) {
                if (p.x != snake.x && p.y != snake.y) {
                    Drawer.appleCoord = p;
                    gen = false;
                    break;
                }
            }
            p = generateNewPoint();
        }

    }

    private Point generateNewPoint(){
        int x = new Random().nextInt(ROWS);
        int y = new Random().nextInt(COLS);

        return new Point(x, y);
    }

    private void newTimer(){
        boolean gen = true;
        Point p = generateNewPoint();

        while (gen) {
            // Vérification si le point généré n'est pas dans le serpent
            for (Point snake : snakeBody) {
                if (p.x != snake.x && p.y != snake.y && p.x != Drawer.appleCoord.x && p.y != Drawer.appleCoord.y) {
                    Drawer.timerCoord = p;
                    gen = false;
                    break;
                }
            }
            p = generateNewPoint();
        }
    }

    private void checkFoodEat() {
        if (pointsAreEquals(snakeHead, new Point(Drawer.appleCoord.x, Drawer.appleCoord.y))) {
            newTimer();
            newFood();
            score++;
            newPlayer(getClass().getResource("/sounds/apple_crunch.wav").toExternalForm()).play();
            snakeBody.add(substractVect(snakeBody.get(snakeBody.size() - 1), currentDirection));
        }
    }

    private void checkTimerEat() {
        if (pointsAreEquals(snakeHead, new Point(Drawer.timerCoord.x, Drawer.timerCoord.y))) {
            newTimer();
            newPlayer(getClass().getResource("/sounds/timer.mp3").toExternalForm()).play();
            time += 5;
        }
    }

    private MediaPlayer newPlayer(String resource){
        Media src = new Media(resource);
        return new MediaPlayer(src);
    }

    private void addDirectionToHead() {
        snakeHead.x += currentDirection.x;
        snakeHead.y += currentDirection.y;
    }

    // Fonctions de calcul
    public static Point substractVect(Point a, Point b) {
        int x = a.x - b.x;
        int y = a.y - b.y;
        return new Point(x, y);
    }

    public static boolean pointsAreEquals(Point a, Point b) {
        boolean x = a.x == b.x;
        boolean y = a.y == b.y;
        return x && y;
    }

}
