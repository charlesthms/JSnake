package me.hypnos.Model;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import me.hypnos.Core.GameMechanics;


public class LargeButton extends Button {

    private final Media UIClickSound = new Media(getClass().getResource("/sounds/ui-click.mp3").toExternalForm());
    private final MediaPlayer mediaPlayer = new MediaPlayer(UIClickSound);

    private final String BUTTON_STYLE = "-fx-background-color: transparent; -fx-background-image: url('"+getClass().getResource("/img/lg-yell.png").toExternalForm()+"');";
    private final String BLUE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('"+getClass().getResource("/img/lg-blue.png").toExternalForm()+"');";

    public LargeButton(String text, int size) {
        setText(text);
        setButtonFont(size);
        setPrefWidth(190);
        setPrefHeight(49);
        setStyle(BUTTON_STYLE);
        InitializeButtonListener();

    }

    private void setButtonFont(int size) {
        setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/Retro_Gaming.ttf"), size));
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
                    Media credits = new Media(getClass().getResource("/sounds/credits.wav").toExternalForm());
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
