public class Battleship {
    public static void main(String[] args) {
        System.out.println(getRandomEndCoordinate(getRandomCoordinate(), 2));
    }

    static final int size = 10;

    static final String ENTER_SHIP_COORDINATE_PROMT = "Geben Sie die %s für ein Schiff der Länge %d ein: ";

    public enum Field {
        FREE, SHIP, SHIP_HIT, WATER_HIT
    }

    public record Coordinate(
            int column,
            int row
    ) {
    }

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

    static void showSeperatorLine() {
        System.out.println("   +-+-+-+-+-+-+-+-+-+-+      +-+-+-+-+-+-+-+-+-+-+");
    }

    static int getMaxSurroundingColumn(final Coordinate start, final Coordinate end) {
        int maxColumn = Math.max(start.column, end.column);

        if (maxColumn == size - 1) {
            return maxColumn;
        } else {
            return maxColumn + 1;
        }
    }

    static int getMinSurroundingColumn(final Coordinate start, final Coordinate end) {
        int minColumn = Math.min(start.column, end.column);

        if (minColumn >= 1) {
            return minColumn - 1;
        } else {
            return minColumn;
        }
    }

    static int getMaxSurroundingRow(final Coordinate start, final Coordinate end) {
        int maxRow = Math.max(start.row, end.row);

        if (maxRow == size - 1) {
            return maxRow;
        } else {
            return maxRow + 1;
        }
    }

    static int getMinSurroundingRow(final Coordinate start, final Coordinate end) {
        int minRow = Math.min(start.row, end.row);

        if (minRow >= 1) {
            return minRow - 1;
        } else {
            return minRow;
        }
    }

    static Coordinate toCoordinate(final String input) {
        int row = Integer.parseInt(input.substring(1)) - 1;
        final String lowerInput = input.toLowerCase();
        int column = Character.getNumericValue(lowerInput.charAt(0)) - 10;

        return new Coordinate(column, row);
    }

    static boolean isValidCoordinate(final String input) {
            return input.matches("[A-J a-j](10|[1-9])");
        }

    static String getStartCoordinatePrompt(final int length){
        return String.format(ENTER_SHIP_COORDINATE_PROMT, "Startkoordinate", length);
    }

    static String getEndCoordinatePrompt(final int length){
        return String.format(ENTER_SHIP_COORDINATE_PROMT, "Endkoordinate", length);
    }

    static void showRowNumber(final int row){
        int rowNumber = row + 1;
        if(rowNumber == 10){
            System.out.print(rowNumber);
        }else {
            System.out.print(" " + rowNumber);
        }
    }

    static Coordinate getRandomEndCoordinate(final Coordinate start, final int distance){
        int columnOrRow = Utility.getRandomInt(2);
        int addOrSubtract = Utility.getRandomInt(2);
        Coordinate endCoordinate = new Coordinate(start.column, start.row);
        if(columnOrRow == 0){
            if(addOrSubtract == 0){
                int column = start.column + distance;
                if (column >= size){
                    column = start.column - distance;
                } endCoordinate = new Coordinate(column, start.row);
            }else if(addOrSubtract == 1){
                int row = start.row - distance;
                if (row < 0){
                    row = start.row + distance;
                } endCoordinate = new Coordinate(start.column, row);
            }
        }else if(columnOrRow == 1){
            if(addOrSubtract == 0){
                int column = start.column + distance;
                if (column >= size){
                    column = start.column - distance;
                } endCoordinate = new Coordinate(column, start.row);
            }else if(addOrSubtract == 1){
                int row = start.row - distance;
                if (row < 0){
                    row = start.row + distance;
                } endCoordinate = new Coordinate(start.column, row);
            }
        }
        return endCoordinate;
    }
}

