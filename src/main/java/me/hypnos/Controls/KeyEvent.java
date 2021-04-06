package me.hypnos.Controls;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import me.hypnos.App;

import java.awt.*;

public class KeyEvent implements EventHandler<javafx.scene.input.KeyEvent> {

    @Override
    public void handle(javafx.scene.input.KeyEvent e) {
        if (e.getCode() == KeyCode.UP && GameMechanics.currentDirection.y != 1) {
            GameMechanics.currentDirection = new Point(0, -1);
        }
        if (e.getCode() == KeyCode.DOWN && GameMechanics.currentDirection.y != -1){
            GameMechanics.currentDirection = new Point(0, 1);
        }
        if (e.getCode() == KeyCode.RIGHT && GameMechanics.currentDirection.x != -1){
            GameMechanics.currentDirection = new Point(1, 0);
        }
        if (e.getCode() == KeyCode.LEFT && GameMechanics.currentDirection.x != 1){
            GameMechanics.currentDirection = new Point(-1, 0);
        }
    }
}
