package me.hypnos.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import me.hypnos.Controls.GameMechanics;
import me.hypnos.Controls.KeyEvent;
import me.hypnos.Model.ButtonStyle;

import java.util.ArrayList;
import java.util.List;


public class ViewManager extends Button {

    GameMechanics gameMechanics;

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;


    private final static int MENU_BUTTON_START_X = 305;
    private final static int MENU_BUTTON_START_Y = 280;

    List<ButtonStyle> menuButtons;
    GraphicsContext gc;

    public ViewManager() {

        menuButtons = new ArrayList<>();

        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, GameMechanics.WIDTH, GameMechanics.HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);

        createBackground();
        createButtons();
        createTitle();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButtons(ButtonStyle btn) {

        btn.setLayoutX(MENU_BUTTON_START_X);
        btn.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);

        menuButtons.add(btn);
        mainPane.getChildren().add(btn);
    }

    private void createButtons() {
        createPlayButton();
        createModeButton();
        createCreditsButton();
        createExitButton();
    }

    private void createPlayButton() {
        ButtonStyle b = new ButtonStyle("JOUER");

        // Listener pour lancer le jeu sur un click
        b.setOnMousePressed(e -> {
            mainStage.hide();
            Stage gameStage = initGameWindow(mainStage);
            gameMechanics = new GameMechanics(gc, gameStage);
        });

        addMenuButtons(b);
    }

    private void createModeButton() {
        ButtonStyle b = new ButtonStyle("MODE");

        addMenuButtons(b);
    }

    private void createCreditsButton() {
        ButtonStyle b = new ButtonStyle("CREDITS");
        addMenuButtons(b);
    }

    private void createExitButton() {
        ButtonStyle b = new ButtonStyle("QUITTER");

        b.setOnMouseClicked(e -> mainStage.close());

        addMenuButtons(b);
    }


    /**
     * <b>Importe et dessine</b> l'arrière-plan du menu principal.
     */
    public void createBackground() {
        Image bg = new Image("file:src/main/java/ressources/img/bg.png", 800, 800, false, true);
        BackgroundImage bgImage = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(bgImage));
    }

    private void createTitle() {
        ImageView title = new ImageView("file:src/main/java/ressources/img/title.png");
        title.setLayoutX(280);
        title.setLayoutY(120);

        mainPane.getChildren().add(title);
    }


    private Stage initGameWindow(Stage stage) {
        // Paramétrage de la fenêtre
        stage.setTitle("JSnake");
        stage.getIcons().add(new Image("file:src/main/java/ressources/img/logo.png"));
        stage.setResizable(false);

        Group root = new Group();

        javafx.scene.canvas.Canvas canvas = new Canvas(GameMechanics.WIDTH, GameMechanics.HEIGHT);
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        gc = canvas.getGraphicsContext2D();

        // Gestion des touches
        scene.setOnKeyPressed(new KeyEvent());

        return stage;
    }
}
