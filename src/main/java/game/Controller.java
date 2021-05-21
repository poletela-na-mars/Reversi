package game;

/**
 * "Реакция" приложения на команды
 */

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import javafx.scene.layout.*;

public class Controller {
    public static final int EIGHT = 8;
    Model logicModel = new Model();
    Stage newWindow;
    Stage newWindowStartNew;

    final Canvas[][] cells = new Canvas[EIGHT][EIGHT];   // Холст для таблицы-поля

    Text labelClose = new Text("Do you really want to exit?");
    Text labelNewGame = new Text("Do you want to start a new game?");

    final Text blackText = new Text(600, 200, "BLACK: ");
    final Text whiteText = new Text(600, 400, "WHITE: ");
    final Text blackScoreText = new Text(680, 200, "");
    final Text whiteScoreText = new Text(680, 400, "");
    final Text whoMove = new Text(265, 45, "");
    final Text whoMoveBl = new Text(330, 45, "");
    final Text whoMoveWh = new Text(330, 45, "");

    final Rectangle whoWinRect = new Rectangle();
    final Text whoWinText = new Text(285, 265, "");

    final GridPane field = new GridPane(); // Таблица-поле

    static final Button newGameButton = new Button("New Game");

    static final Button startButton = new Button("Start");
    static final Button exitButton = new Button("Exit");

    static final Button buttonYesNew = new Button("Yes");
    static final Button buttonNoNew =  new Button("No");

    static final Button closeGameButton = new Button("Exit");
    static final Button buttonYesClose = new Button("Yes");
    static final Button buttonNoClose = new Button("No");

    public Controller(){
        for (int i = 0; i < EIGHT; i++)
            for (int j = 0; j < EIGHT; j++) {
                cells[i][j] = new Canvas(60, 60);    // Ширина и высота ячейки
                field.add(cells[i][j], j, i);
            }
    }

    public void newGameStart() {
        logicModel = new Model();
        for (int i = 0; i < EIGHT; i++)
            for (int j = 0; j < EIGHT; j++) {
                paintCellStyle(i, j, logicModel.getPlayerFromBoard(i, j));  // Получаем циферку, чья фишка
            }

        logicModel.findAvailablePositions();

        for (Pair<Integer, Integer> p : logicModel.getAvailablePositions()) {
            paintCellStyle(p.getKey(), p.getValue(), 3);   // Куда можно поставить фишку - "цветные" фишки
        }

        whiteScoreText.setText(String.valueOf(logicModel.getWhiteScore()));
        blackScoreText.setText(String.valueOf(logicModel.getBlackScore()));

        whoMoveWh.setVisible(false);
        whoMoveBl.setVisible(true);
        whoMove.setText("MOVE: ");
        whoMoveBl.setFill(Color.BLACK);
        whoMoveBl.setText("BLACK");

        whoWinRect.setVisible(false);
        whoWinText.setVisible(false);
    }

    public void nextMove() {
        for (Node grid : field.getChildren()) {
            grid.setOnMouseReleased(new EventHandler<>() {    // Отпускание кнопки
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() != 1) return;
                    Node source = (Node) event.getSource(); // Возвращаем источник события

                    int i = GridPane.getRowIndex(source);
                    int j = GridPane.getColumnIndex(source);

                    boolean currentFlag = false;
                    for (Pair<Integer, Integer> p: logicModel.getAvailablePositions()) {
                        if (p.getValue() == j && p.getKey() == i) {     // Можно ставить, если попадают в "разрешенные" фишки
                            currentFlag = true;
                            break;
                        }
                    }

                    if (currentFlag) {

                        for (Pair<Integer, Integer> p: logicModel.getAvailablePositions()) {
                            paintCellStyle(p.getKey(), p.getValue(), 0);    // Чистка предыдущей
                        }

                        logicModel.findLineForMove(i, j, true); // Обновления на доске + к числу, которые красить

                        for (Pair<Integer, Integer> p: logicModel.getPaintCell()) {
                            paintCellStyle(p.getKey(), p.getValue(),
                                    logicModel.getPlayerFromBoard(p.getKey(), p.getValue()));
                        }

                        logicModel.findAvailablePositions();    // Обновляем placeablePositions

                        if (logicModel.getAvailablePositions().isEmpty())   // Set
                            finishGame();
                        else
                            moveAnotherPlayer();
                    }
                }
            });
        }
    }

    private void paintCellStyle(int i, int j, int style) {
        GraphicsContext cells = this.cells[i][j].getGraphicsContext2D();

        cells.setEffect(null);
        cells.clearRect(0, 0, 60, 60);

        cells.setStroke(Color.BLACK);
        cells.setLineWidth(1.0);
        cells.strokeRect(0, 0, 60, 60);

        // Черные фишки
        if (style == 1) {
            InnerShadow shadow = new InnerShadow();
            cells.setEffect(shadow);
            cells.setFill(Color.DARKSLATEGRAY);
            cells.fillOval(5, 5, 50,50);
            cells.setStroke(Color.BLACK);
            cells.strokeOval(5, 5, 50,50);
        }

        // Белые фишки
        if (style == 2) {
            InnerShadow shadow = new InnerShadow();
            cells.setEffect(shadow);
            cells.setFill(Color.WHITE);
            cells.fillOval(5, 5, 50,50);
            cells.setStroke(Color.BLACK);
            cells.strokeOval(5, 5, 50,50);
        }

        if (style == 3) {
            // "Цветные" клеточки для следующих ходов
            Lighting lighting = new Lighting();
            cells.setEffect(lighting);
            cells.setLineWidth(1.0);
            cells.setFill(Color.CADETBLUE);
            cells.fillRect(0, 0, 60, 60);
        }
    }

    private void moveAnotherPlayer() {
        for (Pair<Integer, Integer> p : logicModel.getAvailablePositions()) {
            paintCellStyle(p.getKey(), p.getValue(), 3);
        }

        if (logicModel.getFlag()) {
            whoMoveBl.setVisible(true);
            whoMoveBl.setFill(Color.BLACK);
            whoMoveBl.setText("BLACK");
            whoMoveWh.setVisible(false);
        }
        else {
            whoMoveWh.setVisible(true);
            whoMoveWh.setFill(Color.WHITE);
            whoMoveWh.setText("WHITE");
            whoMoveBl.setVisible(false);
        }

        whiteScoreText.setText(String.valueOf(logicModel.getWhiteScore()));
        blackScoreText.setText(String.valueOf(logicModel.getBlackScore()));

    }

    private void finishGame() {
        whoWinRect.setVisible(true);
        whoWinText.setVisible(true);

        int blackScore = logicModel.getBlackScore();
        int whiteScore = logicModel.getWhiteScore();

        whiteScoreText.setText(String.valueOf(whiteScore));
        blackScoreText.setText(String.valueOf(blackScore));

        if (blackScore > whiteScore) {
            whoWinText.setFill(Color.BLACK);
            whoWinText.setText("Black Wins!");
        }
        else if (whiteScore > blackScore) {
            whoWinText.setFill(Color.WHITE);
            whoWinText.setText("White Wins!");
        }
        else {
            whoWinText.setFill(Color.DARKSLATEGRAY);
            whoWinText.setText("Drawn Game!");
        }
    }

    public void handleCloseAndNewGameButton(Text label, Button no, Button yes, Stage stage) {
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        label = new Text(label.getText());
        label.setFill(Color.DARKSLATEGRAY);
        label.setFont(Font.font("Franklin Gothic Medium",20));

        StackPane.setAlignment(label, Pos.TOP_CENTER);

        HBox rootOne = new HBox(
                20,
                no, yes
        );

        rootOne.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(
                rootOne, label
        );
        Scene secondScene = new Scene(root, 330, 100);

        stage.setScene(secondScene);

        stage.show();
    }

    public void handleButtonYesNew() {
        newGameStart();
        newWindowStartNew = (Stage) buttonYesNew.getScene().getWindow();
        newWindowStartNew.close();
    }
    public void handleButtonNoNew() {
        newWindowStartNew = (Stage) buttonNoNew.getScene().getWindow();
        newWindowStartNew.close();
    }

    public void handleButtonYesClose(){
        System.exit(0);
    }
    public void handleButtonNoClose() {
        newWindow = (Stage) buttonNoClose.getScene().getWindow();
        newWindow.close();
    }

    public void startGame(Stage stage, Group root, Controller controller) {
        Scene scene = new Scene(root, Color.DARKSEAGREEN);
        stage.setScene(scene);
        controller.newGameStart();
        stage.show();
    }
}
