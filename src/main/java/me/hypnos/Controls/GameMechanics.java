package me.hypnos.Controls;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import me.hypnos.App;
import me.hypnos.Graphics.Drawer;

import java.awt.*;
import java.io.File;

public class GameMechanics {

    public void initSnake() {
        int startingLength = 3;
        for (int i = 0; i < startingLength; i++) {
            App.snakeBody.add(new Point(5, 5));
        }
        App.snakeHead = App.snakeBody.get(0);
    }

    public void move() {

        for (int i = App.snakeBody.size() - 1; i >= 1; i--) {
            App.snakeBody.get(i).x = App.snakeBody.get(i - 1).x;
            App.snakeBody.get(i).y = App.snakeBody.get(i - 1).y;
        }

        addDirectionToHead();
    }

    public static void newFood() {
        boolean gen = true;
        int x = 0;
        int y = 0;

        while (gen) {

            x = (int) (Math.random() * App.ROWS);
            y = (int) (Math.random() * App.COLS);

            // Vérification si le point généré n'est pas dans le serpent
            for (Point snake : App.snakeBody) {
                if (snake.x != x && snake.y != y) {
                    gen = false;
                    break;
                }
            }
        }
        Drawer.appleX = x;
        Drawer.appleY = y;
    }

    public static void checkFoodEat(){
        // Chargement du son
        String path = new File("src/main/java/sounds/apple_crunch.wav").getAbsolutePath();
        Media crunch = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(crunch);

        if (pointsAreEquals(App.snakeHead, new Point(Drawer.appleX, Drawer.appleY))){
            newFood();
            App.score++;
            mediaPlayer.play();
            App.snakeBody.add(substractVect(App.snakeBody.get(App.snakeBody.size() - 1), App.currentDirection));
        }
    }

    private void addDirectionToHead() {
        App.snakeHead.x += App.currentDirection.x;
        App.snakeHead.y += App.currentDirection.y;
    }

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

    public boolean isGameOver() {
        for (int i = 1; i < App.snakeBody.size(); i++) {
            if (pointsAreEquals(App.snakeHead, App.snakeBody.get(i))) return true;
        }

        if (!App.bordersOpen){
            if (App.snakeHead.x > App.WIDTH || App.snakeHead.y > App.HEIGHT) return true;
            return App.snakeHead.x < 0 || App.snakeHead.y < 0;
        }

        return false;
    }

    public static void checkBorders(){
        if (App.bordersOpen){
            if (App.snakeHead.x > App.ROWS) {
                App.snakeHead.x = 0;
            }
            if (App.snakeHead.y > App.COLS) App.snakeHead.y = 0;

            if (App.snakeHead.x < 0) App.snakeHead.x = App.ROWS;
            if (App.snakeHead.y < 0) App.snakeHead.y = App.COLS;
        }
    }
}
