package me.hypnos.Controls;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import me.hypnos.App;

import java.awt.*;

public class KeyEvent implements EventHandler<javafx.scene.input.KeyEvent> {

    @Override
    public void handle(javafx.scene.input.KeyEvent e) {
        if (e.getCode() == KeyCode.UP && App.currentDirection.y != 1) {
            App.currentDirection = new Point(0, -1);
        }
        if (e.getCode() == KeyCode.DOWN && App.currentDirection.y != -1){
            App.currentDirection = new Point(0, 1);
        }
        if (e.getCode() == KeyCode.RIGHT && App.currentDirection.x != -1){
            App.currentDirection = new Point(1, 0);
        }
        if (e.getCode() == KeyCode.LEFT && App.currentDirection.x != 1){
            App.currentDirection = new Point(-1, 0);
        }
    }
}
