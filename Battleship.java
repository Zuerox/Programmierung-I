public class Battleship {
    public static void main(String[] args) {
        Coordinate eins = new Coordinate(9, 2);
        Coordinate zwei = new Coordinate(9, 8);
        int max = getMinSurroundingColumn(eins, zwei);

        System.out.println(max);
    }

    static final int size = 10;

    public enum Field {
        FREE, SHIP, SHIP_HIT, WATER_HIT
    }

    public record Coordinate (
        int column,
        int row
    ) {}

    static int distance(final Coordinate start, final Coordinate end) {
        return Math.abs(start.row - end.row) + Math.abs(start.column - end.column);
    }

    static Coordinate getRandomCoordinate() {
        int RandomColumn = Utility.getRandomInt(size);
        int RandomRow = Utility.getRandomInt(size);
        return new Coordinate(RandomColumn, RandomRow);
    }

    static boolean onOneLine(final Coordinate start, final Coordinate end) {
        return start.column == end.column || start.row == end.row;
    }

    static void showSeperatorLine(){
        System.out.println("   +-+-+-+-+-+-+-+-+-+-+      +-+-+-+-+-+-+-+-+-+-+");
    }

    static int getMaxSurroundingColumn(final Coordinate start, final Coordinate end) {
        int maxColumn = Math.max(start.column, end.column);

        if (maxColumn == size - 1) {
            return maxColumn;
        }else {
            return maxColumn + 1;
        }
    }

    static int getMinSurroundingColumn(final Coordinate start, final Coordinate end) {
        int minColumn = Math.min(start.column, end.column);

        if (minColumn >= 1) {
            return minColumn - 1;
        }else {
            return minColumn;
        }
    }

    static int getMaxSurroundingRow(final Coordinate start, final Coordinate end) {
        int maxRow = Math.max(start.row, end.row);

        if (maxRow == size - 1) {
            return maxRow;
        }else {
            return maxRow + 1;
        }
    }

    static int getMinSurroundingRow(final Coordinate start, final Coordinate end) {
        int minRow = Math.min(start.row, end.row);

        if (minRow >= 1) {
            return minRow - 1;
        }else {
            return minRow;
        }
    }
}


