package game;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.matcher.control.LabeledMatchers;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model logicModel = new Model();

    @Test
    void findLineForMove() {
        logicModel = new Model();
        assertTrue(logicModel.findLineForMove(3, 2, false));        // Нашли

        logicModel = new Model();
        assertFalse(logicModel.findLineForMove(3, 3, true));        // Не нашли, так как стоит фишка на этом месте

        logicModel = new Model();
        assertFalse(logicModel.findLineForMove(4, 5, true)); // Неверно указан move, должно быть (false),
        // поскольку на той позиции фишка отсутсвует

        logicModel = new Model();
        assertTrue(logicModel.findLineForMove(2, 3, false));        // Нашли

        logicModel = new Model();
        assertFalse(logicModel.findLineForMove(0, 1, false));   // Не нашли, клеточка не для размещения

    }

    @Test
    void findAvailablePositions() {
        logicModel = new Model();
        logicModel.findAvailablePositions();

        Set<Pair<Integer, Integer>> coordinates = new HashSet<>();
        coordinates.add(new Pair<>(3, 2));
        coordinates.add(new Pair<>(2, 3));
        coordinates.add(new Pair<>(5, 4));
        coordinates.add(new Pair<>(4, 5));

        assertEquals(coordinates, logicModel.getAvailablePositions());
    }

    @Test
    void findAvailablePositionsAfterMove() {
        logicModel = new Model();

        logicModel.findLineForMove(2, 3, true); // Устанавливаем

        logicModel.findAvailablePositions();

        Set<Pair<Integer, Integer>> coordinates = new HashSet<>();
        coordinates.add(new Pair<>(2, 2));
        coordinates.add(new Pair<>(2, 4));
        coordinates.add(new Pair<>(4, 2));

        assertEquals(coordinates, logicModel.getAvailablePositions());
    }

    @Test
    void oneMove() {
        logicModel = new Model();
        List<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        logicModel.findLineForMove(2, 3, true);     //  Установим сюда фишку
        coordinates.add(new Pair<>(2, 3));
        coordinates.add(new Pair<>(3, 3));
        assertEquals(coordinates, logicModel.getPaintCell());
        assertFalse(logicModel.getFlag());      // Управление у черных
        assertEquals(4, logicModel.getBlackScore());
        assertEquals(1, logicModel.getWhiteScore());
    }

    @Test
    void twoMoves() {
        logicModel = new Model();
        List<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        logicModel.findLineForMove(5, 4, true);     //  Установим сюда фишку
        coordinates.add(new Pair<>(5, 4));
        coordinates.add(new Pair<>(4, 4));
        assertEquals(coordinates, logicModel.getPaintCell());
        logicModel.findLineForMove(5, 5, true);     // Потом еще одну установим сюда
        coordinates.clear();      // В findLineForMove после каждого вызова происходит очистка списка
        coordinates.add(new Pair<>(5, 5));
        coordinates.add(new Pair<>(4, 4));
        assertEquals(coordinates, logicModel.getPaintCell());
        assertTrue(logicModel.getFlag());      // После первого хода - управление будет у белых
        assertEquals(3, logicModel.getBlackScore());
        assertEquals(3, logicModel.getWhiteScore());
    }
}


@ExtendWith(ApplicationExtension.class)
class ClickableButtonTest_JUnit5Hamcrest {

    @Test
    void should_contain_button_with_text() {
        Controller controller = new Controller();
        FxAssert.verifyThat(controller.newGameButton, LabeledMatchers.hasText("New Game"));
        FxAssert.verifyThat(controller.exitButton, LabeledMatchers.hasText("Exit"));
        FxAssert.verifyThat(controller.closeGameButton, LabeledMatchers.hasText("Exit"));
        FxAssert.verifyThat(controller.startButton, LabeledMatchers.hasText("Start"));
        FxAssert.verifyThat(controller.buttonYesNew, LabeledMatchers.hasText("Yes"));
        FxAssert.verifyThat(controller.buttonYesClose, LabeledMatchers.hasText("Yes"));
        FxAssert.verifyThat(controller.buttonNoNew, LabeledMatchers.hasText("No"));
        FxAssert.verifyThat(controller.buttonNoClose, LabeledMatchers.hasText("No"));
    }
}


