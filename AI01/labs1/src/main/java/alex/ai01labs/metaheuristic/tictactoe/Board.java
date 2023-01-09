package alex.ai01labs.metaheuristic.tictactoe;

import java.util.ArrayList;
import java.util.List;

import static alex.ai01labs.metaheuristic.tictactoe.GameApp.BOARD_SIZE;

public class Board {

    private List<Cell> emptyCells;
    private CellState[][] board;
    private List<Cell> rootValues;

    public Board() {
        initBoard();
    }

    public List<Cell> getRootValues() {
        return rootValues;
    }

    public Cell getBestMove() {
        int max = Integer.MIN_VALUE;
        int best = Integer.MIN_VALUE;

        for (int i = 0; i < rootValues.size(); i++) {
            if (max < rootValues.get(i).getMinimaxValue()) {
                max = rootValues.get(i).getMinimaxValue();
                best = i;
            }
        }
        return rootValues.get(best);
    }

    private void initBoard() {
        rootValues = new ArrayList<>();
        board = new CellState[BOARD_SIZE][BOARD_SIZE];
    }

    public boolean isWinning(CellState player) {
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        return false;
    }

    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = Integer.MIN_VALUE;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = Integer.MIN_VALUE;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public boolean isRunning() {
        if (isWinning(CellState.COMPUTER)) {
            return false;
        }
        if (isWinning(CellState.USER)) {
            return false;
        }
        return !getEmptyCells().isEmpty();
    }

    private List<Cell> getEmptyCells() {
        emptyCells = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == CellState.EMPTY) {
                    emptyCells.add(new Cell(i, j));
                }
            }
        }
        return emptyCells;
    }

    public void displayBoard() {
        System.out.println();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

    }

    public boolean move(Cell cell, CellState player) {
        if (board[cell.getX()][cell.getY()] == CellState.EMPTY) {
            board[cell.getX()][cell.getY()] = player;
            return true;
        } else {
            System.out.printf("\t Bad cell: %s, already occupied by: %s\n", cell, board[cell.getX()][cell.getY()]);
            return false;
        }
    }

    public void setup() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = CellState.EMPTY;
            }
        }
    }


    public void callMinimax(int depth, CellState player) {
        rootValues.clear();
        minimax(depth, player);
    }

    private int minimax(int depth, CellState player) {
        List<Cell> availableCells = getEmptyCells();
        {// leaf cases - end of the game
            if (isWinning(CellState.COMPUTER)) {
                return +1;
            }

            if (isWinning(CellState.USER)) {
                return -1;
            }


            if (availableCells.isEmpty()) {
                return 0;
            }
        }

        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < availableCells.size(); i++) {
            Cell point = availableCells.get(i);
            if (player == CellState.COMPUTER) {
                move(point, CellState.COMPUTER);
                int currentScore = minimax(1, CellState.USER);
                scores.add(currentScore);
                // this is the root node of the game tree: this is the value we are after
                //+1: computer take move and can win
                //-1: computer should avoid this move (lose situation)
                if (depth == 0) {
                    point.setMinimaxValue(currentScore);
                    rootValues.add(point);
                }
            } else if (player == CellState.USER) {
                move(point, CellState.USER);
                scores.add(minimax(depth + 1, CellState.COMPUTER));
            }
            board[point.getX()][point.getY()] = CellState.EMPTY;
        }
        if (player == CellState.COMPUTER) {
            return returnMax(scores);
        }
        return returnMin(scores);
    }
}
