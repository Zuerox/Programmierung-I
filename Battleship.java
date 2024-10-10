public class Battleship {
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
        if(start.column == end.column && start.row == end.row) {
           return true ;
        } else {
            return false;
        }
    }

    static void showSeperatorLine(){
        System.out.println("   +-+-+-+-+-+-+-+-+-+-+      +-+-+-+-+-+-+-+-+-+-+");
    }

    static int getMaxSurroundingColumn(final Coordinate start, final Coordinate end) {
        int maxColumn;
        if(start.column >= end.column) {
            maxColumn = start.column;
        }else {
            maxColumn = end.column;
        }
        if (maxColumn == size) {
            return maxColumn - 1;
        }else {
            return maxColumn;
        }
    }

    static int getMinSurroundingColumn(final Coordinate start, final Coordinate end) {
        int minColumn;
        if(start.column <= end.column) {
            minColumn = start.column;
        }else {
            minColumn = end.column;
        }
        if (minColumn >= 1) {
            return minColumn - 1;
        }else {
            return minColumn;
        }
    }

    static int getMaxSurroundingRow(final Coordinate start, final Coordinate end) {
        int maxRow;
        if(start.column >= end.column) {
            maxRow = start.column;
        }else {
            maxRow = end.column;
        }
        if (maxRow == size) {
            return maxRow - 1;
        }else {
            return maxRow;
        }
    }

    static int getMinSurroundingRow(final Coordinate start, final Coordinate end) {
        int minRow;
        if(start.column <= end.column) {
            minRow = start.column;
        }else {
            minRow = end.column;
        }
        if (minRow >= 1) {
            return minRow - 1;
        }else {
            return minRow;
        }
    }
}


