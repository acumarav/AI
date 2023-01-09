package alex.ai01labs.metaheuristic.tictactoe;

public enum CellState {
    COMPUTER("X"),
    USER("O"),
    EMPTY("-");

    private final String text;

    CellState(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
