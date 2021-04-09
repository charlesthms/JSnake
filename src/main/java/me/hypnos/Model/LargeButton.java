package me.hypnos.Model;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import me.hypnos.Core.GameMechanics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LargeButton extends Button {

    private final String path = new File("src/main/java/resources/sounds/ui-click.mp3").getAbsolutePath();
    private final Media UIClickSound = new Media(new File(path).toURI().toString());
    private final MediaPlayer mediaPlayer = new MediaPlayer(UIClickSound);

    private final String FONT_PATH = "src/main/java/resources/fonts/Retro_Gaming.ttf";
    private final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('file:src/main/java/resources/img/lg-yell.png');";
    private final String BLUE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('file:src/main/java/resources/img/lg-blue.png');";

    public LargeButton(String text, int size) {

        setText(text);
        setButtonFont(size);
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_STYLE);
        InitializeButtonListener();

    }

    private void setButtonFont(int size) {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), size));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    private void setButtonStyle() {
        setStyle(BUTTON_STYLE);
        setLayoutY(getLayoutY() - 4);
    }

    private void setBlueStyle(){
        setStyle(BLUE_STYLE);
        setLayoutY(getLayoutY() - 4);
    }

    private void resetY(){
        setLayoutY(getLayoutY() + 4);
    }

    private void InitializeButtonListener() {

        setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                if (getText().equals("AVEC CHRONO") || getText().equals("SANS CHRONO")){
                    if (GameMechanics.timedMode){
                        setBlueStyle();
                        setText("SANS CHRONO");
                        GameMechanics.timedMode = false;
                    } else {
                        setButtonStyle();
                        setText("AVEC CHRONO");
                        GameMechanics.timedMode = true;
                    }
                } else if (getText().equals("CREDITS")){
                    String path = new File("src/main/java/resources/sounds/credits.wav").getAbsolutePath();
                    Media credits = new Media(new File(path).toURI().toString());
                    MediaPlayer cr = new MediaPlayer(credits);
                    setButtonStyle();
                    cr.play();
                } else {
                    setButtonStyle();
                }

                mediaPlayer.play();
            }


        });

        setOnMouseReleased(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                resetY();
                mediaPlayer.stop();
            }
        });
    }

}
