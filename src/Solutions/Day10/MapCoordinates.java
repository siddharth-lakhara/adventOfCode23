package Solutions.Day10;

public class MapCoordinates {

    public int row;
    public int col;

    public Direction direction;

    public MapCoordinates(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void updateCoordinates() {
        switch (direction) {
            case UP:
                row--;
                break;
            case DOWN:
                row++;
                break;
            case LEFT:
                col--;
                break;
            case RIGHT:
                col++;
                break;
        }
    }

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}
