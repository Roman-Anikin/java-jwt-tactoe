package web.model;

public class GameFieldDto {

    private final String[][] field;

    public GameFieldDto() {
        field = new String[3][3];
    }

    public GameFieldDto(String[][] field) {
        this.field = field;
    }

    public String[][] getField() {
        return field;
    }

    public void setX(int row, int col) {
        field[row][col] = "X";
    }

    public void setO(int row, int col) {
        field[row][col] = "O";
    }
}
