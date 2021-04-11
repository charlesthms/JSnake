package me.hypnos.Model;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import me.hypnos.Core.GameMechanics;


public class SmallButton extends Button {

    private final Media UIClickSound = new Media(getClass().getResource("/sounds/ui-click.mp3").toExternalForm());
    private final MediaPlayer mediaPlayer = new MediaPlayer(UIClickSound);

    private final String DARK_STYLE = "-fx-background-color: rgba(0,0,0,0); -fx-background-image: url('"+getClass().getResource("/img/small-btn-dark.png").toExternalForm()+"');";
    private final String LIGHT_STYLE = "-fx-background-color: rgba(0,0,0,0); -fx-background-image: url('"+getClass().getResource("/img/small-btn-light.png").toExternalForm()+"');";
    private final String RED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('"+getClass().getResource("/img/small-btn-red.png").toExternalForm()+"');";


    public SmallButton(String text, String defaultStyle) {

        setText(text);
        setButtonFont();
        setPrefWidth(95);
        setPrefHeight(49);

        if (defaultStyle.equals("THEME")){
            setStyle(DARK_STYLE);
        } else if (defaultStyle.equals("BORDS")){
            setStyle(LIGHT_STYLE);
        }

        InitializeButtonListener();

    }

    private void setButtonFont() {
        setFont(Font.loadFont(getClass().getResourceAsStream("/fonts/Retro_Gaming.ttf"), 15));
    }

    private void setLightStyle() {
        setStyle(LIGHT_STYLE);
        setLayoutY(getLayoutY() + 4);
    }

    private void setDarkStyle() {
        setStyle(DARK_STYLE);
        setLayoutY(getLayoutY() + 4);
    }

    private void setRedStyle() {
        setStyle(RED_STYLE);
        setLayoutY(getLayoutY() + 4);
    }

    private void resetY(){
        setLayoutY(getLayoutY() - 4);
    }

    private void InitializeButtonListener() {

        setOnMousePressed(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) {

                if (getText().equals("CLAIR") || getText().equals("SOMBRE")) {
                    if (GameMechanics.enableDarkMode){
                        setLightStyle();
                        setText("CLAIR");
                        GameMechanics.enableDarkMode = false;
                    } else {
                        setDarkStyle();
                        setText("SOMBRE");
                        GameMechanics.enableDarkMode = true;
                    }
                } else {
                    if (GameMechanics.bordersOpen){
                        setLightStyle();
                        setText("AVEC\nBORDS");
                        GameMechanics.bordersOpen = false;
                    } else {
                        setRedStyle();
                        setText("SANS\nBORDS");
                        GameMechanics.bordersOpen = true;
                    }
                }

            }
            mediaPlayer.play();
        });

        setOnMouseReleased(e ->{
            resetY();
            mediaPlayer.stop();
        });
    }

}

