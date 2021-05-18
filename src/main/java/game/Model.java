package game;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Описывает внутреннюю логику, общается с внешним миром через API классов/функций
 */

public class Model {
    private final int[][] board = new int[8][8];

    private boolean moveFlag;

    private boolean cantMoveStepBlack;
    private boolean cantMoveStepWhite;

    private final Set<Pair<Integer, Integer>> availablePositions = new HashSet<>();

    private final List<Pair<Integer, Integer>> paintCell = new ArrayList<>();   //  Список с координатами фишек, что закрасим
    // Для подсчета результатов

    private int blackScore;
    private int whiteScore;

    // Начало игры
    public Model() {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
            }

        board[3][3] = 2;    // White
        board[4][4] = 2;
        board[3][4] = 1;    // Black
        board[4][3] = 1;

        moveFlag = true;

        cantMoveStepBlack = false;
        cantMoveStepWhite = false;

        blackScore = 2;
        whiteScore = 2;
    }

    /**
     * Ходы возможны по горизонтали, вертикали и диагональным линиям. Выбираем, от какой фишки будем ходить.
     */

    public boolean findLineForMove(int i, int j, boolean move) {
        paintCell.clear();

        if (move) paintCell.add(new Pair<>(i, j));

        int currentPlayer = moveFlag ? 1 : 2;

        // Помечаем доску 1 и 2, тем самым отмечая, где чей ход
        // Ходы справа
        if (j != 7) {
            int newJ = j;
            int count = 1;
            while (newJ < 7) {
                newJ++;
                if (board[i][newJ] == 0) break;
                if (board[i][newJ] == currentPlayer) {
                    if (count != 1) {       // При count 1 мы считаем фишку, от которой начинается отсчет, и при дальнейшем увеличении - то, сколько закрасим
                        // Наименьший вариант, сколько закрасить (2 = фишка врага + новая наша)
                        if (!move) return true;

                        for (int m = j; m < newJ; m++)
                            board[i][m] = currentPlayer;

                        for (int m = j + 1; m < newJ; m++)
                            paintCell.add(new Pair<>(i, m));
                    }

                    break;
                }
                count++;
            }
        }

        // Ходы слева
        if (j != 0) {
            int newJ = j;
            int count = 1;
            while (newJ > 0) {
                newJ--;
                if (board[i][newJ] == 0) break;
                if (board[i][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        for (int m = j; m > newJ; m--)
                            board[i][m] = currentPlayer;

                        for (int m = j - 1; m > newJ; m--)
                            paintCell.add(new Pair<>(i, m));
                    }

                    break;
                }
                count++;
            }
        }

        // Ходы снизу
        if (i != 7) {
            int newI = i;
            int count = 1;
            while (newI < 7) {
                newI++;
                if (board[newI][j] == 0) break;
                if (board[newI][j] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        for (int m = i; m < newI; m++)
                            board[m][j] = currentPlayer;

                        for (int m = i + 1; m < newI; m++)
                            paintCell.add(new Pair<>(m, j));
                    }

                    break;
                }
                count++;
            }
        }

        // Ходы сверху
        if (i != 0) {
            int newI = i;
            int count = 1;
            while (newI > 0) {
                newI--;
                if (board[newI][j] == 0) break;
                if (board[newI][j] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        for (int m = i; m > newI; m--)
                            board[m][j] = currentPlayer;

                        for (int m = i - 1; m > newI; m--)
                            paintCell.add(new Pair<>(m, j));
                    }

                    break;
                }
                count++;
            }
        }

        // Нижняя правая диагональ от (i, j)
        if (i != 7 && j != 7) {
            int newI = i;
            int newJ = j;
            int count = 1;
            while (newI < 7 && newJ < 7) {
                newI++;
                newJ++;
                if (board[newI][newJ] == 0) break;
                if (board[newI][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        for (int k = i, m = j; k < newI && m < newJ; k++, m++) {
                            board[k][m] = currentPlayer;
                        }

                        for (int k = i + 1, m = j + 1; k < newI && m < newJ; k++, m++) {
                            paintCell.add(new Pair<>(k, m));
                        }
                    }

                    break;
                }
                count++;
            }
        }

        // Нижняя левая диагональ от (i, j)
        if (i != 7 && j != 0) {
            int newI = i;
            int newJ = j;
            int count = 1;
            while (newI < 7 && newJ > 0) {
                newI++;
                newJ--;
                if (board[newI][newJ] == 0) break;
                if (board[newI][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        for (int k = i, m = j; k < newI && m > newJ; k++, m--) {
                            board[k][m] = currentPlayer;
                        }

                        for (int k = i + 1, m = j - 1; k < newI && m > newJ; k++, m--) {
                            paintCell.add(new Pair<>(k, m));
                        }
                    }

                    break;
                }
                count++;
            }
        }

        // Верхняя левая диагональ от (i, j)
        if (i != 0 && j != 0) {
            int newI = i;
            int newJ = j;
            int count = 1;
            while (newI > 0 && newJ > 0) {
                newI--;
                newJ--;
                if (board[newI][newJ] == 0) break;
                if (board[newI][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;

                        for (int k = i, m = j; k > newI && m > newJ; k--, m--) {
                            board[k][m] = currentPlayer;
                        }

                        for (int k = i - 1, m = j - 1; k > newI && m > newJ; k--, m--) {
                            paintCell.add(new Pair<>(k, m));
                        }
                    }

                    break;
                }
                count++;
            }
        }

        // Верхняя правая диагональ от (i, j)
        if (i != 0 && j != 7) {
            int newI = i;
            int newJ = j;
            int count = 1;
            while (newI > 0 && newJ < 7) {
                newI--;
                newJ++;
                if (board[newI][newJ] == 0) break;
                if (board[newI][newJ] == currentPlayer) {
                    if (count != 1) {
                        if (!move) return true;


                        for (int k = i, m = j; k > newI && m < newJ; k--, m++) {
                            board[k][m] = currentPlayer;
                        }

                        for (int k = i - 1, m = j + 1; k > newI && m < newJ; k--, m++) {
                            paintCell.add(new Pair<>(k, m));
                        }
                    }

                    break;
                }
                count++;
            }
        }

        if (move) {
            updateScores();
            moveFlag = !moveFlag;
        }

        return false;
    }

    // Ячеечки для новых ходов
    public void findAvailablePositions() {
        if (cantMoveStepBlack && cantMoveStepWhite) {
            paintCell.clear();
            return;
        }

        availablePositions.clear(); // Set
        int previousPlayer = moveFlag ? 2 : 1;

        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (board[i][j] == previousPlayer) {
                    if (i < 7) {
                        if (board[i + 1][j] == 0)
                            if (findLineForMove(i + 1, j, false))
                                availablePositions.add(new Pair<>(i + 1, j));
                        if (j < 7)
                            if (board[i + 1][j + 1] == 0)
                                if (findLineForMove(i + 1, j + 1, false))
                                    availablePositions.add(new Pair<>(i + 1, j + 1));
                        if (j > 0)
                            if (board[i + 1][j - 1] == 0)
                                if (findLineForMove(i + 1, j - 1, false))
                                    availablePositions.add(new Pair<>(i + 1, j - 1));
                    }

                    if (i > 0) {
                        if (board[i - 1][j] == 0)
                            if (findLineForMove(i - 1, j, false))
                                availablePositions.add(new Pair<>(i - 1, j));
                        if (j < 7)
                            if (board[i - 1][j + 1] == 0)
                                if (findLineForMove(i - 1, j + 1, false))
                                    availablePositions.add(new Pair<>(i - 1, j + 1));
                        if (j > 0)
                            if (board[i - 1][j - 1] == 0)
                                if (findLineForMove(i - 1, j - 1, false))
                                    availablePositions.add(new Pair<>(i - 1, j - 1));
                    }

                    if (j < 7)
                        if (board[i][j + 1] == 0)
                            if (findLineForMove(i, j + 1, false))
                                availablePositions.add(new Pair<>(i, j + 1));

                    if (j > 0)
                        if (board[i][j - 1] == 0)
                            if (findLineForMove(i, j - 1, false))
                                availablePositions.add(new Pair<>(i, j - 1));
                }

        if (availablePositions.isEmpty()) {
            if (moveFlag)
                cantMoveStepBlack = true;
            else
                cantMoveStepWhite = true;

            moveFlag = !moveFlag;
            findAvailablePositions();
        }

        cantMoveStepWhite = false;
        cantMoveStepBlack = false;
    }

    // Увеличиваем число фишек, съевшего, и уменьшаем число фишек, съеденного
        private void updateScores() {
            if (moveFlag) {
                blackScore +=  paintCell.size();
                whiteScore -=  paintCell.size() - 1;
            } else {
                whiteScore +=  paintCell.size();
                blackScore -= paintCell.size() - 1;
            }
        }

    public List<Pair<Integer, Integer>> getPaintCell() { return paintCell; }

    public Set<Pair<Integer, Integer>> getAvailablePositions() { return availablePositions; }

    public int getWhiteScore() { return whiteScore; }

    public int getBlackScore() { return blackScore; }

    public boolean getFlag() { return moveFlag; }

    public int getPlayerFromBoard(int i, int j) { return board[i][j]; }
}

