package alex.ai01labs.metaheuristic.tictactoe;

import java.util.Random;
import java.util.Scanner;

import static alex.ai01labs.metaheuristic.tictactoe.GameApp.BOARD_SIZE;

public class Game {
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);
    private Board board;

    public Game() {
        initGame();
        displayBoard();
        makeFirstMove();
        playGame();
        checkStatus();
    }

    private void initGame() {
        board = new Board();
        board.setup();
    }

    private void playGame() {
        while (board.isRunning()) {
            //1 System.out.println("User move:");

           /* var x = scanner.nextInt();
            var y = scanner.nextInt();
            Cell userCell = new Cell(x, y);
            board.move(userCell, CellState.USER);*/
            makeUserMove();
            board.displayBoard();
            if (!board.isRunning()) {
                break;
            }
            board.callMinimax(0, CellState.COMPUTER);
            for (Cell cell : board.getRootValues()) {
                System.out.printf("Cell values: %s minimaxValue: %d \n", cell, cell.getMinimaxValue());
            }
            board.move(board.getBestMove(), CellState.COMPUTER);
            board.displayBoard();
        }
    }

    private void makeFirstMove() {
        System.out.println("Who starts? 1 - Computer; 2 - User");
        int choice = scanner.nextInt();
        if (choice == 1) {
            Cell cell = new Cell(random.nextInt(BOARD_SIZE), random.nextInt(BOARD_SIZE));
            board.move(cell, CellState.COMPUTER);
            board.displayBoard();
        }
    }

    private void makeUserMove() {
        boolean moveDone = false;
        do {
            System.out.println("User's move: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            System.out.printf("\t (%d,%d)\t", x, y);
            if (x >= BOARD_SIZE || y >= BOARD_SIZE) {
                System.out.println("x,y should be LESS than " + BOARD_SIZE);
            } else {
                Cell cell = new Cell(x, y);
                moveDone = board.move(cell, CellState.USER);
            }

        } while (!moveDone);
    }

    private void checkStatus() {
        if (board.isWinning(CellState.COMPUTER)) {
            System.out.println("Computer has won");
        } else if (board.isWinning(CellState.USER)) {
            System.out.println("The user has won...");
        } else {
            System.out.println("It is  a draw");
        }
    }

    private void displayBoard() {
        board.displayBoard();
    }
}
