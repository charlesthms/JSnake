package me.hypnos.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.hypnos.App;
import me.hypnos.Controls.GameMechanics;

import java.awt.*;
import java.util.List;

public class Drawer {

    public static Image apple = new Image("file:src/main/java/img/apple1.png");
    public static Image snakeTailImg;
    public static Image snakeHeadImg = new Image("file:src/main/java/img/head_right.png");
    public static Image snakeBodyImg;

    public static int appleX;
    public static int appleY;


    public void drawBackground(GraphicsContext gc) {
        for (int y = 0; y < App.ROWS; y++) {
            for (int x = 0; x < App.COLS; x++) {
                if ((y + x) % 2 == 0) {
                    //gc.setFill(Color.web("AAD751"));
                    gc.setFill(Color.rgb(40, 40, 40));
                } else {
                    //gc.setFill(Color.web("A2D149"));
                    gc.setFill(Color.rgb(33, 33, 33));
                }
                gc.fillRect(x * App.sqrSize, y * App.sqrSize, App.sqrSize, App.sqrSize);
            }
        }
    }

    public void drawSnake(GraphicsContext gc) {

        List<Point> sl = App.snakeBody;

        // Orientation et dessin de la tête
        snakeHeadOrientation();
        gc.drawImage(snakeHeadImg, App.snakeHead.x * App.sqrSize, App.snakeHead.y * App.sqrSize, App.sqrSize, App.sqrSize);

        for (int i = 1; i < sl.size() - 1; i++) {

            // Gestion de la pliure du corp
            Point previousVect = GameMechanics.substractVect(sl.get(i + 1), sl.get(i));
            Point nextVect = GameMechanics.substractVect(sl.get(i - 1), sl.get(i));

            if (previousVect.x == nextVect.x){
                snakeBodyImg = new Image("file:src/main/java/img/body_vertical.png");
            } else if (previousVect.y == nextVect.y) {
                snakeBodyImg = new Image("file:src/main/java/img/body_horizontal.png");
            } else {
                if (previousVect.x == -1 && nextVect.y == -1 || previousVect.y == -1 && nextVect.x == -1) {
                    snakeBodyImg = new Image("file:src/main/java/img/body_topleft.png");
                } else if (previousVect.x == -1 && nextVect.y == 1 || previousVect.y == 1 && nextVect.x == -1){
                    snakeBodyImg = new Image("file:src/main/java/img/body_bottomleft.png");
                } else if (previousVect.x == 1 && nextVect.y == -1 || previousVect.y == -1 && nextVect.x == 1){
                    snakeBodyImg = new Image("file:src/main/java/img/body_topright.png");
                } else if (previousVect.x == 1 && nextVect.y == 1 || previousVect.y == 1 && nextVect.x == 1){
                    snakeBodyImg = new Image("file:src/main/java/img/body_bottomright.png");
                }
            }
            gc.drawImage(snakeBodyImg, sl.get(i).getX() * App.sqrSize, sl.get(i).getY() * App.sqrSize, App.sqrSize, App.sqrSize);
        }

        // Orientation et dessin de la queue
        snakeTailOrientation();
        int index = sl.size() - 1;
        gc.drawImage(snakeTailImg, sl.get(index).getX() * App.sqrSize, sl.get(index).getY() * App.sqrSize, App.sqrSize, App.sqrSize);
    }

    public void drawFood(GraphicsContext gc) {
        gc.drawImage(apple, appleX * App.sqrSize, appleY * App.sqrSize, App.sqrSize, App.sqrSize);
    }

    public static void snakeHeadOrientation() {

        Point direction = GameMechanics.substractVect(App.snakeBody.get(0), App.snakeBody.get(1));

        // UP
        if (GameMechanics.pointsAreEquals(direction, App.vectUp)) {
            snakeHeadImg = new Image("file:src/main/java/img/head_up.png");
        }
        // DOWN
        if (GameMechanics.pointsAreEquals(direction, App.vectDown)) {
            snakeHeadImg = new Image("file:src/main/java/img/head_down.png");
        }
        // RIGHT
        if (GameMechanics.pointsAreEquals(direction, App.vectRight)) {
            snakeHeadImg = new Image("file:src/main/java/img/head_right.png");
        }
        // LEFT
        if (GameMechanics.pointsAreEquals(direction, App.vectLeft)) {
            snakeHeadImg = new Image("file:src/main/java/img/head_left.png");
        }
    }

    public static void snakeTailOrientation() {

        Point direction = GameMechanics.substractVect(App.snakeBody.get(App.snakeBody.size() - 2), App.snakeBody.get(App.snakeBody.size() - 1));

        // UP
        if (GameMechanics.pointsAreEquals(direction, App.vectUp)) {
            snakeTailImg = new Image("file:src/main/java/img/tail_down.png");
        }
        // DOWN
        if (GameMechanics.pointsAreEquals(direction, App.vectDown)) {
            snakeTailImg = new Image("file:src/main/java/img/tail_up.png");
        }
        // RIGHT
        if (GameMechanics.pointsAreEquals(direction, App.vectRight)) {
            snakeTailImg = new Image("file:src/main/java/img/tail_left.png");
        }
        // LEFT
        if (GameMechanics.pointsAreEquals(direction, App.vectLeft)) {
            snakeTailImg = new Image("file:src/main/java/img/tail_right.png");
        }
    }

    public void drawScore(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 255, 255));
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + App.score, 10, 35);
    }

}
