package game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author poletela-na-mars (Polina Postylyakova)
 */

/**
 * Описывает механизмы взаимодействия с пользователем
 */

public class View extends Application {

    @Override
    public void start(Stage stage) {

        Controller controller = new Controller();

        stage.setHeight(600);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle("Reversi");

        Image imageIcon = new Image("icon_main_new_4.png");
        stage.getIcons().add(imageIcon);

        controller.field.setLayoutX(80);
        controller.field.setLayoutY(65);
        controller.field.setGridLinesVisible(true);
        controller.field.setOnMousePressed((e) -> controller.nextMove());

        controller.blackText.setFont(Font.font("Franklin Gothic Medium", 20));
        controller.blackText.setFill(Color.DARKSLATEGRAY);
        controller.blackScoreText.setFont(Font.font("Franklin Gothic Medium", 20));
        controller.blackScoreText.setFill(Color.DARKSLATEGRAY);
        controller.whiteText.setFont(Font.font("Franklin Gothic Medium", 20));
        controller.whiteText.setFill(Color.DARKSLATEGRAY);
        controller.whiteScoreText.setFont(Font.font("Franklin Gothic Medium", 20));
        controller.whiteScoreText.setFill(Color.DARKSLATEGRAY);
        controller.whoMove.setFont(Font.font("Franklin Gothic Medium",20));
        controller.whoMove.setFill(Color.DARKSLATEGRAY);
        controller.whoMoveBl.setFont(Font.font("Franklin Gothic Medium",20));
        controller.whoMoveBl.setFill(Color.BLACK);
        controller.whoMoveWh.setFont(Font.font("Franklin Gothic Medium",20));
        controller.whoMoveWh.setFill(Color.WHITE);

        controller.newGameButton.setLayoutX(615);
        controller.newGameButton.setLayoutY(250);
        controller.newGameButton.setOnMouseClicked((e) -> {
            stage.setOpacity(0.5);
            controller.handleNewGameStart();
            controller.buttonNoNew.setOnMouseClicked((event) -> {
                stage.setOpacity(1);
                controller.handleButtonNoNew();
            });
            controller.buttonYesNew.setOnMouseClicked((event) -> {
                stage.setOpacity(1);
                controller.handleButtonYesNew();
            });
        });
        controller.newGameButton.setFont(Font.font("Franklin Gothic Medium",20));
        controller.newGameButton.setStyle("-fx-background-color: cadetblue; ");
        controller.newGameButton.setTextFill(Color.WHITE);

        controller.buttonYesNew.setStyle("-fx-background-color: cadetblue; ");
        controller.buttonYesNew.setTextFill(Color.WHITE);
        controller.buttonYesNew.setFont(Font.font("Franklin Gothic Medium",18));

        controller.buttonNoNew.setStyle("-fx-background-color: cadetblue; ");
        controller.buttonNoNew.setTextFill(Color.WHITE);
        controller.buttonNoNew.setFont(Font.font("Franklin Gothic Medium",18));

        controller.closeGameButton.setLayoutX(615);
        controller.closeGameButton.setLayoutY(300);
        controller.closeGameButton.setOnMouseClicked((e) -> {
            stage.setOpacity(0.5);
            controller.closeButtonAction();
            controller.buttonNoClose.setOnMouseClicked((event) -> {
                stage.setOpacity(1);
                controller.handleButtonNoClose();
            });
            controller.buttonYesClose.setOnMouseClicked((event) -> {
                stage.setOpacity(1);
                controller.handleButtonYesClose();
            });
        });
        controller.closeGameButton.setStyle("-fx-background-color: cadetblue; ");
        controller.closeGameButton.setTextFill(Color.WHITE);
        controller.closeGameButton.setFont(Font.font("Franklin Gothic Medium",20));

        controller.buttonYesClose.setStyle("-fx-background-color: cadetblue; ");
        controller.buttonYesClose.setTextFill(Color.WHITE);
        controller.buttonYesClose.setFont(Font.font("Franklin Gothic Medium",18));

        controller.buttonNoClose.setStyle("-fx-background-color: cadetblue; ");
        controller.buttonNoClose.setTextFill(Color.WHITE);
        controller.buttonNoClose.setFont(Font.font("Franklin Gothic Medium",18));

        controller.whoWinRect.setX(240);
        controller.whoWinRect.setY(235);
        controller.whoWinRect.setWidth(200);
        controller.whoWinRect.setHeight(50);
        controller.whoWinRect.setFill(Color.CADETBLUE);
        controller.whoWinText.setFont(Font.font("Franklin Gothic Medium", 20));
        controller.whoWinRect.setStroke(Color.DARKCYAN);
        controller.whoWinRect.setStrokeWidth(2.0);

        stage.initStyle(StageStyle.UNDECORATED);

        Group root = new Group(
                controller.whiteText,
                controller.whiteScoreText, controller.newGameButton,
                controller.blackText, controller.blackScoreText,
                controller.field, controller.whoMove,
                controller.whoWinRect, controller.whoWinText,
                controller.closeGameButton, controller.whoMoveBl,
                controller.whoMoveWh
        );

        controller.startButton.setLayoutX(300);
        controller.startButton.setLayoutY(500);
        controller.startButton.setOnMouseClicked((e) -> controller.startGame(stage, root, controller));
        controller.startButton.setStyle("-fx-background-color: darkslategray; ");
        controller.startButton.setTextFill(Color.WHITE);
        controller.startButton.setFont(Font.font("Franklin Gothic Medium",25));

        controller.exitButton.setLayoutX(300);
        controller.exitButton.setLayoutY(500);
        controller.exitButton.setOnMouseClicked((e) -> controller.handleButtonYesClose());
        controller.exitButton.setStyle("-fx-background-color: darkslategray; ");
        controller.exitButton.setTextFill(Color.WHITE);
        controller.exitButton.setFont(Font.font("Franklin Gothic Medium",25));

        Image image = new Image("menu_Reversi_P.png");
        ImageView imageView = new ImageView(image);

        VBox rootTwo = new VBox(
                20,
                controller.startButton, controller.exitButton
        );

        rootTwo.setAlignment(Pos.CENTER);

        StackPane rootPrimary = new StackPane(
                imageView, rootTwo
        );

        Scene primaryScene = new Scene(rootPrimary);
        stage.setScene(primaryScene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
