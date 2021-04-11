package me.hypnos.Graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import me.hypnos.Core.GameMechanics;

import java.awt.*;
import java.util.List;

public class Drawer {

    public Image apple = new Image(getClass().getResource("/img/apple1.png").toExternalForm());
    public Image timer = new Image(getClass().getResource("/img/chronometer.png").toExternalForm());
    public Image snakeTailImg;
    public Image snakeHeadImg = new Image(getClass().getResource("/img/head_right.png").toExternalForm());
    public Image snakeBodyImg;

    public static Point appleCoord;
    public static Point timerCoord = new Point();


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
                snakeBodyImg = new Image(getClass().getResource("/img/body_vertical.png").toExternalForm());
            } else if (previousVect.y == nextVect.y) {
                snakeBodyImg = new Image(getClass().getResource("/img/body_horizontal.png").toExternalForm());
            } else {
                if (previousVect.x == -1 && nextVect.y == -1 || previousVect.y == -1 && nextVect.x == -1) {
                    snakeBodyImg = new Image(getClass().getResource("/img/body_topleft.png").toExternalForm());
                } else if (previousVect.x == -1 && nextVect.y == 1 || previousVect.y == 1 && nextVect.x == -1) {
                    snakeBodyImg = new Image(getClass().getResource("/img/body_bottomleft.png").toExternalForm());
                } else if (previousVect.x == 1 && nextVect.y == -1 || previousVect.y == -1 && nextVect.x == 1) {
                    snakeBodyImg = new Image(getClass().getResource("/img/body_topright.png").toExternalForm());
                } else if (previousVect.x == 1 && nextVect.y == 1 || previousVect.y == 1 && nextVect.x == 1) {
                    snakeBodyImg = new Image(getClass().getResource("/img/body_bottomright.png").toExternalForm());
                }
            }
            gc.drawImage(snakeBodyImg, sl.get(i).getX() * GameMechanics.sqrSize, sl.get(i).getY() * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);
        }

        // Orientation et dessin de la queue
        snakeTailOrientation();
        int index = sl.size() - 1;
        gc.drawImage(snakeTailImg, sl.get(index).getX() * GameMechanics.sqrSize, sl.get(index).getY() * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);
    }

    public void drawApple(GraphicsContext gc) {
        gc.drawImage(apple, appleCoord.x * GameMechanics.sqrSize, appleCoord.y * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);
    }

    public void drawTimer(GraphicsContext gc){
        gc.drawImage(timer, timerCoord.x * GameMechanics.sqrSize, timerCoord.y * GameMechanics.sqrSize, GameMechanics.sqrSize, GameMechanics.sqrSize);
    }

    public void snakeHeadOrientation() {

        Point direction = GameMechanics.substractVect(GameMechanics.snakeBody.get(0), GameMechanics.snakeBody.get(1));

        // UP
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectUp)) {
            snakeHeadImg = new Image(getClass().getResource("/img/head_up.png").toExternalForm());
        }
        // DOWN
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectDown)) {
            snakeHeadImg = new Image(getClass().getResource("/img/head_down.png").toExternalForm());
        }
        // RIGHT
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectRight)) {
            snakeHeadImg = new Image(getClass().getResource("/img/head_right.png").toExternalForm());
        }
        // LEFT
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectLeft)) {
            snakeHeadImg = new Image(getClass().getResource("/img/head_left.png").toExternalForm());
        }
    }

    public void snakeTailOrientation() {

        Point direction = GameMechanics.substractVect(GameMechanics.snakeBody.get(GameMechanics.snakeBody.size() - 2), GameMechanics.snakeBody.get(GameMechanics.snakeBody.size() - 1));

        // UP
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectUp)) {
            snakeTailImg = new Image(getClass().getResource("/img/tail_down.png").toExternalForm());
        }
        // DOWN
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectDown)) {
            snakeTailImg = new Image(getClass().getResource("/img/tail_up.png").toExternalForm());
        }
        // RIGHT
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectRight)) {
            snakeTailImg = new Image(getClass().getResource("/img/tail_left.png").toExternalForm());
        }
        // LEFT
        if (GameMechanics.pointsAreEquals(direction, GameMechanics.vectLeft)) {
            snakeTailImg = new Image(getClass().getResource("/img/tail_right.png").toExternalForm());
        }
    }

    public void drawScore(GraphicsContext gc) {
        gc.setFill(Color.rgb(255, 255, 255));
        gc.setFont(new Font("Digital-7", 20));
        gc.fillText("Score: " + GameMechanics.score, 10, 35);
    }

    public void drawTimer(GraphicsContext gc, long time){
        gc.setFill(Color.rgb(255, 255, 255));
        gc.setFont(new Font("Digital-7", 20));
        gc.fillText("Temps: " + time + "s", 100, 35);
    }

}
