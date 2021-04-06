package me.hypnos.Controls;

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

public class GameMechanics {

    private Drawer drawer;
    private Timeline timeline;
    private Stage stage;

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
    public static boolean enableDarkMode = false;
    public static int score = 0;


    public GameMechanics(GraphicsContext gc, Stage gameStage) {
        // Initialisation
        drawer = new Drawer();
        stage = gameStage;

        // Démarrage d'un nouveau jeu
        resetGameState();
        initSnake();
        newFood();
        drawElements(gc);
        startTimer(gc);
    }

    // ################################################ UTILITAIRES ################################################# //

    public void startTimer(GraphicsContext gc) {
        // Timer du jeu
        timeline = new Timeline(new KeyFrame(Duration.millis(150), e -> updateGameStatus(gc)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Initialise le serpent à une taille de <b>3</b>.
     */
    public void initSnake() {
        int startingLength = 3;
        for (int i = 0; i < startingLength; i++) {
            snakeBody.add(new Point(5, 5));
        }
        snakeHead = snakeBody.get(0);
    }

    public boolean isGameOver() {
        for (int i = 1; i < snakeBody.size(); i++) {
            // Si la tête touche le corp
            if (pointsAreEquals(snakeHead, snakeBody.get(i))) return true;
        }
        // Si la tête dépasse une bordure (uniquement en mode avec bordures)
        return checkBorders();
    }

    public boolean isGameWon() {
        return snakeBody.size() == COLS * ROWS;
    }

    public static boolean checkBorders() {
        if (bordersOpen) {
            if (snakeHead.x > ROWS) snakeHead.x = 0;
            if (snakeHead.y > COLS) snakeHead.y = 0;
            if (snakeHead.x < 0) snakeHead.x = ROWS;
            if (snakeHead.y < 0) snakeHead.y = COLS;
            return false;
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
        isGameOver();

        // Si le jeu est perdant
        if (isGameOver()) {
            String path = new File("src/main/java/ressources/sounds/game_over.wav").getAbsolutePath();
            Media gameOver = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(gameOver);

            mediaPlayer.play();
            timeline.stop();
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
        move();
        drawer.drawFood(gc);
        drawer.drawSnake(gc);
    }

    public void move() {
        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
        addDirectionToHead();
    }

    /**
     * Génère une pomme aléatoirement sur le plateau
     */
    public static void newFood() {
        boolean gen = true;
        int x = 0;
        int y = 0;

        while (gen) {

            x = (int) (Math.random() * ROWS);
            y = (int) (Math.random() * COLS);

            // Vérification si le point généré n'est pas dans le serpent
            for (Point snake : snakeBody) {
                if (snake.x != x && snake.y != y) {
                    gen = false;
                    break;
                }
            }
        }
        Drawer.appleX = x;
        Drawer.appleY = y;
    }

    public static void checkFoodEat() {
        // Chargement du son
        String path = new File("src/main/java/ressources/sounds/apple_crunch.wav").getAbsolutePath();
        Media crunch = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(crunch);

        if (pointsAreEquals(snakeHead, new Point(Drawer.appleX, Drawer.appleY))) {
            newFood();
            score++;
            mediaPlayer.play();
            snakeBody.add(substractVect(snakeBody.get(snakeBody.size() - 1), currentDirection));
        }
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
