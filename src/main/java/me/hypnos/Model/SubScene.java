package me.hypnos.Model;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class SubScene extends javafx.scene.SubScene {

    AnchorPane subPanel;

    private final String bg = "file:src/main/java/resources/img/bg-panel.png";

    private boolean isVisible;

    public SubScene() {
        super(new AnchorPane(), 800, 800);
        prefWidth(800);
        prefHeight(800);

        BackgroundImage img = new BackgroundImage(new Image(bg), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        subPanel = (AnchorPane) this.getRoot();
        subPanel.setBackground(new Background(img));

        isVisible = false;

        setLayoutX(0);
        setLayoutY(-800);
    }

    public void addLargeButton(LargeButton btn) {
        subPanel.getChildren().add(btn);
    }

    public void addSmallButton(SmallButton btn) {
        subPanel.getChildren().add(btn);
    }

    /**
     * Animation de transition entre les menus
     */
    public void moveSubScene() {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(.3));
        transition.setNode(this);

        if (isVisible) {
            transition.setToY(-800);
            isVisible = false;
        } else {
            transition.setToY(800);
            isVisible = true;
        }

        transition.play();
    }
}
