package me.hypnos.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.hypnos.Controls.GameMechanics;

import java.awt.*;
import java.util.List;

public class Drawer {

    public static Image apple = new Image("file:src/main/java/ressources/img/apple1.png");
    public static Image snakeTailImg;
    public static Image snakeHeadImg = new Image("file:src/main/java/ressources/img/head_right.png");
    public static Image snakeBodyImg;

    public static int appleX;
    public static int appleY;


    /**
     * Dessine l'arrière-plan selon le mode sélectionné
     *
     * @param gc graphics context
     */
    public void drawBackground(GraphicsContext gc) {
        for (int y = 0; y < GameMechanics.ROWS; y++) {
            for (int x = 0; x < GameMechanics.COLS; x++) {
                if ((y + x) % 2 == 0) {
                    if (GameMechanics.enableDarkMode) gc.setFill(Color.rgb(40, 40, 40));
                    else gc.setFill(Color.web("AAD751"));
                } else {
                    if (GameMechanics.enableDarkMode) gc.setFill(Color.rgb(33, 33, 33));
                    else gc.setFill(Color.web("A2D149"));

                }
                gc.fillRect(x * GameMechanics.sqrSize, y * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);
            }
        }
    }

    public void drawSnake(GraphicsContext gc) {

        List<Point> sl = GameMechanics.snakeBody;

        // Orientation et dessin de la tête
        snakeHeadOrientation();
        gc.drawImage(snakeHeadImg, GameMechanics.snakeHead.x * GameMechanics.sqrSize, GameMechanics.snakeHead.y * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);

        for (int i = 1; i < sl.size() - 1; i++) {

            // Gestion de la pliure du corp : on extrait les deltas vecteurs pour exprimer le différentiel entre la
            // position observée et les positions suivantes et précédentes
            Point previousVect = GameMechanics.substractVect(sl.get(i + 1), sl.get(i));
            Point nextVect = GameMechanics.substractVect(sl.get(i - 1), sl.get(i));

            if (previousVect.x == nextVect.x) {
                // Si le Delta Vect préc. et le Delta Vect suiv. sont égaux alors la position observée à le même vecteur donc la même image
                snakeBodyImg = new Image("file:src/main/java/ressources/img/body_vertical.png");
            } else if (previousVect.y == nextVect.y) {
                snakeBodyImg = new Image("file:src/main/java/ressources/img/body_horizontal.png");
            } else {
                if (previousVect.x == -1 && nextVect.y == -1 || previousVect.y == -1 && nextVect.x == -1) {
                    snakeBodyImg = new Image("file:src/main/java/ressources/img/body_topleft.png");
                } else if (previousVect.x == -1 && nextVect.y == 1 || previousVect.y == 1 && nextVect.x == -1) {
                    snakeBodyImg = new Image("file:src/main/java/ressources/img/body_bottomleft.png");
                } else if (previousVect.x == 1 && nextVect.y == -1 || previousVect.y == -1 && nextVect.x == 1) {
                    snakeBodyImg = new Image("file:src/main/java/ressources/img/body_topright.png");
                } else if (previousVect.x == 1 && nextVect.y == 1 || previousVect.y == 1 && nextVect.x == 1) {
                    snakeBodyImg = new Image("file:src/main/java/ressources/img/body_bottomright.png");
                }
            }
            gc.drawImage(snakeBodyImg, sl.get(i).getX() * GameMechanics.sqrSize, sl.get(i).getY() * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);
        }

        // Orientation et dessin de la queue
        snakeTailOrientation();
        int index = sl.size() - 1;
        gc.drawImage(snakeTailImg, sl.get(index).getX() * GameMechanics.sqrSize, sl.get(index).getY() * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);
    }

    public void drawFood(GraphicsContext gc) {
        gc.drawImage(apple, appleX * GameMechanics.sqrSize, appleY * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);
    }

    public static void snakeHeadOrientation() {

        Point direction = GameMechanics.substractVect(GameMechanics.snakeBody.get(0), GameMechanics.snakeBody.get(1));

        // UP
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectUp)) {
            snakeHeadImg = new Image("file:src/main/java/ressources/img/head_up.png");
        }
        // DOWN
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectDown)) {
            snakeHeadImg = new Image("file:src/main/java/ressources/img/head_down.png");
        }
        // RIGHT
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectRight)) {
            snakeHeadImg = new Image("file:src/main/java/ressources/img/head_right.png");
        }
        // LEFT
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectLeft)) {
            snakeHeadImg = new Image("file:src/main/java/ressources/img/head_left.png");
        }
    }

    public static void snakeTailOrientation() {

        Point direction = GameMechanics.substractVect(GameMechanics.snakeBody.get(GameMechanics.snakeBody.size() - 2), GameMechanics.snakeBody.get(GameMechanics.snakeBody.size() - 1));

        // UP
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectUp)) {
            snakeTailImg = new Image("file:src/main/java/ressources/img/tail_down.png");
        }
        // DOWN
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectDown)) {
            snakeTailImg = new Image("file:src/main/java/ressources/img/tail_up.png");
        }
        // RIGHT
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectRight)) {
            snakeTailImg = new Image("file:src/main/java/ressources/img/tail_left.png");
        }
        // LEFT
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectLeft)) {
            snakeTailImg = new Image("file:src/main/java/ressources/img/tail_right.png");
        }
    }

    public void drawScore(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 255, 255));
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + GameMechanics.score, 10, 35);
    }

}
