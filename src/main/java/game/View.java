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
        controller.nextMove();

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

        Controller.newGameButton.setLayoutX(615);
        Controller.newGameButton.setLayoutY(250);
        Controller.newGameButton.setOnMouseClicked((e) -> {
            stage.setOpacity(0.5);
            controller.handleCloseAndNewGameButton(controller.labelNewGame, Controller.buttonNoNew,
                    Controller.buttonYesNew, controller.newWindowStartNew);
            Controller.buttonNoNew.setOnMouseClicked((event) -> {
                stage.setOpacity(1);
                controller.handleButtonNoNew();
            });
            Controller.buttonYesNew.setOnMouseClicked((event) -> {
                stage.setOpacity(1);
                controller.handleButtonYesNew();
            });
        });
        Controller.newGameButton.setFont(Font.font("Franklin Gothic Medium",20));
        Controller.newGameButton.setStyle("-fx-background-color: cadetblue; ");
        Controller.newGameButton.setTextFill(Color.WHITE);

        Controller.buttonYesNew.setStyle("-fx-background-color: cadetblue; ");
        Controller.buttonYesNew.setTextFill(Color.WHITE);
        Controller.buttonYesNew.setFont(Font.font("Franklin Gothic Medium",18));

        Controller.buttonNoNew.setStyle("-fx-background-color: cadetblue; ");
        Controller.buttonNoNew.setTextFill(Color.WHITE);
        Controller.buttonNoNew.setFont(Font.font("Franklin Gothic Medium",18));

        Controller.closeGameButton.setLayoutX(615);
        Controller.closeGameButton.setLayoutY(300);
        Controller.closeGameButton.setOnMouseClicked((e) -> {
            stage.setOpacity(0.5);
            controller.handleCloseAndNewGameButton(controller.labelClose, Controller.buttonNoClose,
                    Controller.buttonYesClose, controller.newWindow);
            Controller.buttonNoClose.setOnMouseClicked((event) -> {
                stage.setOpacity(1);
                controller.handleButtonNoClose();
            });
            Controller.buttonYesClose.setOnMouseClicked((event) -> {
                stage.setOpacity(1);
                controller.handleButtonYesClose();
            });
        });
        Controller.closeGameButton.setStyle("-fx-background-color: cadetblue; ");
        Controller.closeGameButton.setTextFill(Color.WHITE);
        Controller.closeGameButton.setFont(Font.font("Franklin Gothic Medium",20));

        Controller.buttonYesClose.setStyle("-fx-background-color: cadetblue; ");
        Controller.buttonYesClose.setTextFill(Color.WHITE);
        Controller.buttonYesClose.setFont(Font.font("Franklin Gothic Medium",18));

        Controller.buttonNoClose.setStyle("-fx-background-color: cadetblue; ");
        Controller.buttonNoClose.setTextFill(Color.WHITE);
        Controller.buttonNoClose.setFont(Font.font("Franklin Gothic Medium",18));

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
                controller.whiteScoreText, Controller.newGameButton,
                controller.blackText, controller.blackScoreText,
                controller.field, controller.whoMove,
                controller.whoWinRect, controller.whoWinText,
                Controller.closeGameButton, controller.whoMoveBl,
                controller.whoMoveWh
        );

        Controller.startButton.setLayoutX(300);
        Controller.startButton.setLayoutY(500);
        Controller.startButton.setOnMouseClicked((e) -> controller.startGame(stage, root, controller));
        Controller.startButton.setStyle("-fx-background-color: darkslategray; ");
        Controller.startButton.setTextFill(Color.WHITE);
        Controller.startButton.setFont(Font.font("Franklin Gothic Medium",25));

        Controller.exitButton.setLayoutX(300);
        Controller.exitButton.setLayoutY(500);
        Controller.exitButton.setOnMouseClicked((e) -> controller.handleButtonYesClose());
        Controller.exitButton.setStyle("-fx-background-color: darkslategray; ");
        Controller.exitButton.setTextFill(Color.WHITE);
        Controller.exitButton.setFont(Font.font("Franklin Gothic Medium",25));

        Image image = new Image("menu_Reversi_P.png");
        ImageView imageView = new ImageView(image);

        VBox rootTwo = new VBox(
                20,
                Controller.startButton, Controller.exitButton
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


