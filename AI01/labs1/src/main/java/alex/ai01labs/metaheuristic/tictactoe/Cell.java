package alex.ai01labs.metaheuristic.tictactoe;

public class Cell {
    private final int x;
    private final int y;
    private int minimaxValue;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMinimaxValue() {
        return minimaxValue;
    }

    public void setMinimaxValue(int minimaxValue) {
        this.minimaxValue = minimaxValue;
    }

    @Override
    public String toString() {
        return String.format("%d,%d", x, y);
    }
}
