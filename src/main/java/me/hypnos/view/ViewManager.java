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
import me.hypnos.Core.GameMechanics;
import me.hypnos.Core.KeyEvent;
import me.hypnos.Model.LargeButton;
import me.hypnos.Model.SmallButton;
import me.hypnos.Model.SubScene;

import java.util.ArrayList;
import java.util.List;


public class ViewManager extends Button {

    private GameMechanics gameMechanics;
    private GraphicsContext gc;

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;


    private final static int MENU_BUTTON_START_X = 305;
    private final static int MENU_BUTTON_START_Y = 280;

    private SubScene modeSubScene;

    private List<LargeButton> menuButtons;
    private List<SmallButton> modeButtons;


    public ViewManager() {

        menuButtons = new ArrayList<>();
        modeButtons = new ArrayList<>();

        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, GameMechanics.WIDTH, GameMechanics.HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        mainStage.setTitle("Snake - By Hypnos");
        mainStage.getIcons().add(new Image(getClass().getResource("/img/icon.png").toExternalForm()));

        createBackground();
        createButtons();
        createTitle();

        createSubScene();
    }

    private void createSubScene(){
        createModeSubScene();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButtons(LargeButton btn) {
        btn.setLayoutX(MENU_BUTTON_START_X);
        btn.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);

        menuButtons.add(btn);
        mainPane.getChildren().add(btn);
    }

    private void createModeSubScene(){
        modeSubScene = new SubScene();

        createThemesButtons();

        // Ajout du bouton de retour
        createReturnButton();
        createModeButton();

        addAllButtons();

        // Ajout de la sous-scène à la scène principale
        mainPane.getChildren().add(modeSubScene);
    }

    private void addAllButtons(){
        for (SmallButton btn : modeButtons){
            modeSubScene.addSmallButton(btn);
        }
    }

    private void createThemesButtons(){
        SmallButton colorMode = new SmallButton("SOMBRE", "THEME");
        SmallButton bordsMode = new SmallButton("AVEC\nBORD", "BORDS");

        colorMode.setLayoutX(280);
        colorMode.setLayoutY(169);

        bordsMode.setLayoutX(425);
        bordsMode.setLayoutY(169);

        modeButtons.add(colorMode);
        modeButtons.add(bordsMode);
    }

    private void createReturnButton(){
        LargeButton largeButton = new LargeButton("RETOUR", 23);

        largeButton.setLayoutX(305);
        largeButton.setLayoutY(600);

        modeSubScene.addLargeButton(largeButton);

        largeButton.setOnAction(e -> {
            modeSubScene.moveSubScene();
        });
    }

    private void createModeButton(){

        LargeButton lb = new LargeButton("AVEC CHRONO", 18);

        lb.setLayoutX(305);
        lb.setLayoutY(300);

        modeSubScene.addLargeButton(lb);
    }

    private void createButtons() {
        createPlayButton();
        createOptionsButton();
        createCreditsButton();
        createExitButton();
    }

    private void createPlayButton() {
        LargeButton b = new LargeButton("JOUER", 23);

        // Listener pour lancer le jeu sur un click
        b.setOnMousePressed(e -> {
            mainStage.hide();
            Stage gameStage = initGameWindow(mainStage);
            gameMechanics = new GameMechanics(gc, gameStage);
        });

        addMenuButtons(b);
    }

    private void createOptionsButton() {
        LargeButton b = new LargeButton("OPTIONS", 23);
        addMenuButtons(b);

        b.setOnAction(e -> {
            modeSubScene.moveSubScene();
        });
    }

    private void createCreditsButton() {
        LargeButton b = new LargeButton("CREDITS", 23);
        addMenuButtons(b);
    }

    private void createExitButton() {
        LargeButton b = new LargeButton("QUITTER", 23);

        b.setOnMouseClicked(e -> mainStage.close());

        addMenuButtons(b);
    }


    /**
     * <b>Importe et dessine</b> l'arrière-plan du menu principal.
     */
    public void createBackground() {
        Image bg = new Image(getClass().getResource("/img/bg.png").toExternalForm(), 800, 800, false, true);
        BackgroundImage bgImage = new BackgroundImage(bg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(bgImage));
    }

    private void createTitle() {
        ImageView title = new ImageView(getClass().getResource("/img/title.png").toExternalForm());
        title.setLayoutX(280);
        title.setLayoutY(120);

        mainPane.getChildren().add(title);
    }


    private Stage initGameWindow(Stage stage) {
        // Paramétrage de la fenêtre
        stage.setTitle("Snake - By Hypnos");
        stage.getIcons().add(new Image(getClass().getResource("/img/icon.png").toExternalForm()));
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
