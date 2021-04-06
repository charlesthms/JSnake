package me.hypnos.Model;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ButtonStyle extends Button {

    private final String path = new File("src/main/java/ressources/sounds/ui-click.mp3").getAbsolutePath();
    private final Media UIClickSound = new Media(new File(path).toURI().toString());
    private final MediaPlayer mediaPlayer = new MediaPlayer(UIClickSound);

    private final String FONT_PATH = "src/main/java/ressources/fonts/Retro_Gaming.ttf";
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('https://raw.githubusercontent.com/dhakalkumar/spacerunner-game/master/src/model/resources/yellow_button_pressed.png');";
    private final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('https://raw.githubusercontent.com/dhakalkumar/spacerunner-game/master/src/model/resources/yellow_button.png');";

    public ButtonStyle(String text) {

        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_STYLE);
        InitializeButtonListener();

    }

    private void setButtonFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonStyle() {
        setStyle(BUTTON_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    private void InitializeButtonListener() {

        setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                setButtonPressedStyle();
                mediaPlayer.play();
            }
        });

        setOnMouseReleased(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {
                setButtonStyle();
                mediaPlayer.stop();
            }
        });

    }

}
