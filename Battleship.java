import java.io.IOException;
import java.util.ArrayList;

public class Battleship {
    public static void main(String[] args) {
        System.out.println();
        Field[][] otherField = initOtherField();
        Field[][] ownField = initOwnField(otherField);
        while (!endCondition(ownField, otherField)) {
            turn(ownField, otherField);
        }
        outputWinner(ownField, otherField);
    }

    static final int size = 10;

    static final String ENTER_SHIP_COORDINATE_PROMPT = "Geben Sie die %s für ein Schiff der Länge %d ein: ";

    public enum Field {
        WATER, SHIP, SHIP_HIT, WATER_HIT
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

    static String getStartCoordinatePrompt(final int length) {
        return String.format(ENTER_SHIP_COORDINATE_PROMPT, "Startkoordinate", length);
    }

    static String getEndCoordinatePrompt(final int length) {
        return String.format(ENTER_SHIP_COORDINATE_PROMPT, "Endkoordinate", length);
    }

    static void showRowNumber(final int row) {
        int rowNumber = row + 1;
        if (rowNumber == 10) {
            System.out.print(rowNumber);
        } else {
            System.out.print(" " + rowNumber);
        }
    }

    static Coordinate getRandomEndCoordinate(final Coordinate start, final int distance) {
        int columnOrRow = Utility.getRandomInt(2); //entscheidet, ob mit der Reihe oder Zeile gerechnet wird
        int addOrSubtract = Utility.getRandomInt(2); //entscheidet, ob addiert oder subtrahiert wird
        Coordinate endCoordinate = new Coordinate(start.column, start.row);
        if (columnOrRow == 0) { //es wird mit der Zeile gerechnet
            if (addOrSubtract == 0) { //es wird addiert
                int column = start.column + distance;
                if (column >= size) { //es wird geprüft ob neue Koordinate innerhalb des Feldes liegt
                    column = start.column - distance;
                }
                endCoordinate = new Coordinate(column, start.row);
            } else if (addOrSubtract == 1) {
                int column = start.column - distance;
                if (column < 0) {
                    column = start.column + distance;
                }
                endCoordinate = new Coordinate(column, start.row);
            }
        } else if (columnOrRow == 1) {
            if (addOrSubtract == 0) {
                int row = start.row + distance;
                if (row >= size) {
                    row = start.row - distance;
                }
                endCoordinate = new Coordinate(start.column, row);
            } else if (addOrSubtract == 1) {
                int row = start.row - distance;
                if (row < 0) {
                    row = start.row + distance;
                }
                endCoordinate = new Coordinate(start.column, row);
            }
        }
        return endCoordinate;
    }

    static void showField(final Field field, final boolean showShips) {
        switch (field) {
            case WATER_HIT:
                System.out.print("x");
                break;
            case SHIP:
                if (showShips) {
                    System.out.print("o");
                } else {
                    System.out.print(" ");
                }
                break;
            case SHIP_HIT:
                System.out.print("*");
                break;
            case WATER:
            default:
                System.out.print(" ");
        }
    }

    static void placeShip(final Coordinate start, final Coordinate end, final Field[][] field) {
        if (start.column == end.column) { //prüft, ob Schiff einer Zeile entlang gesetzt werden soll
            if (start.row < end.row) { //prüft in welche Richtung das Schiff gesetzt werden muss
                for (int i = start.row; i <= end.row; i++) {
                    field[start.column][i] = Field.SHIP;
                }
            } else {
                for (int i = start.row; i >= end.row; i--) {
                    field[end.column][i] = Field.SHIP;
                }
            }
        } else { //Schiff muss einer Spalte entlang platziert werden
            if (start.column < end.column) {
                for (int i = start.column; i <= end.column; i++) {
                    field[i][start.row] = Field.SHIP;
                }
            } else {
                for (int i = start.column; i >= end.column; i--) {
                    field[i][end.row] = Field.SHIP;
                }
            }
        }
    }

    static void showRow(final int row, final Field[][] ownField, final Field[][] otherField) {
        showRowNumber(row);
        System.out.print(" |");
        for (int i = 0; i < size; i++) {
            showField(ownField[i][row], true);
            System.out.print("|");
        }
        System.out.print("   ");
        showRowNumber(row);
        System.out.print(" |");
        for (int i = 0; i < size; i++) {
            showField(otherField[i][row], false);
            System.out.print("|");
        }
        System.out.println();
    }

    static void showFields(final Field[][] ownField, final Field[][] otherField) {
        System.out.println("    A B C D E F G H I J        A B C D E F G H I J");
        showSeperatorLine();
        for (int i = 0; i < size; i++) {
            showRow(i, ownField, otherField);
            showSeperatorLine();
        }
        System.out.println();
    }

    static boolean shipSunk(final Coordinate shot, final Field[][] field) {
        //Alle Felder Rechts von shot werden überprüft
        for (int i = shot.row(); i < size; i++) {
            if (field[shot.column][i] == Field.SHIP) {
                return false;
            } else if (field[shot.column][i] != Field.SHIP_HIT) { //nicht SHIP_HIT anstatt == WATER || WATER_HIT
                break;
            }
        }
        //Alle Felder Links von shot werden überprüft
        for (int i = shot.row(); i > 0; i--) {
            if (field[shot.column][i] == Field.SHIP) {
                return false;
            } else if (field[shot.column][i] != Field.SHIP_HIT) {
                break;
            }
        }

        //Alle Felder über shot werden überprüft
        for (int i = shot.column; i < size; i++) {
            if (field[i][shot.row] == Field.SHIP) {
                return false;
            } else if (field[i][shot.row] != Field.SHIP_HIT) {
                break;
            }
        }

        //Alle Felder unter shot werden überprüft
        for (int i = shot.column; i > 0; i--) {
            if (field[i][shot.row] == Field.SHIP) {
                return false;
            } else if (field[i][shot.row] != Field.SHIP_HIT) {
                break;
            }
        }
        return true;
    }

    static void setAllFree(final Field[][] field) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = Field.WATER;
            }
        }
    }

    static int countHits(final Field[][] field) {
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == Field.SHIP_HIT) {
                    count++;
                }
                ;
            }
        }
        return count;
    }

    /*static void fillWaterHits(final Coordinate shot, final Field[][] field) {
        int cnt = 1;
        int min;
        int max;
        if (field[shot.column + 1][shot.row] == Field.SHIP_HIT || field[shot.column - 1][shot.row] == Field.SHIP_HIT) {
            //Schiff verläuft vertikal
            min = shot.column();
            max = shot.column();
            //Start und Endkoordinate(vertikal) vom Schiff werden festgelegt
            while (field[shot.column - cnt][shot.row] == Field.SHIP_HIT) {
                min++;
                cnt++;
            }
            cnt = 1;
            while (field[shot.column + cnt][shot.row] == Field.SHIP_HIT) {
                max++;
                cnt++;
            }
            //Die Felder über und unter dem Schiff werden als getroffenes Wasser markiert
            if(max < 9) {
                field[max + 1][shot.row] = Field.WATER_HIT;
            }
            if(min > 1) {
                field[min - 1][shot.row] = Field.WATER_HIT;
            }
            //restlichen Felder entlang des Schiffes werden richtig markiert
            for (int i = min; i <= max; i++) {
               if(shot.row + 1 < 9) {
                   field[i][shot.row + 1] = Field.WATER_HIT;
               }
               if(shot.row > 1){
                   field[i][shot.row - 1] = Field.WATER_HIT;
               }
            }
        } else {
            //Schiff verläuft horizontal
            min = shot.row();
            max = shot.row();
            //Start und Endkoordinate(horizontal) vom Schiff werden festgelegt
            while (field[shot.column][shot.row - cnt] == Field.SHIP_HIT) {
                min++;
                cnt++;
            }
            cnt = 1;
            while (field[shot.column][shot.row + cnt] == Field.SHIP_HIT) {
                max++;
                cnt++;
            }
            //Die Felder links und rechts vom Schiff werden als getroffenes Wasser markiert.
            if(max < 9){
                field[shot.column][max + 1] = Field.WATER_HIT;
            }
            if(min > 1){
                field[shot.column][min - 1] = Field.WATER_HIT;
            }
            //restlichen Felder entlang des Schiffes werden richtig markiert
            for (int i = min; i <= max; i++) {
                if(shot.column < 9){
                    field[shot.column + 1][i] = Field.WATER_HIT;
                }
                if(shot.column > 1){
                    field[shot.column - 1][i] = Field.WATER_HIT;
                }
            }
        }
    }*/

    /*static boolean noConflict(final Coordinate start, final Coordinate end, final Field[][] field) {
        if (start.column == end.column) { //prüft, ob Schiff einer Zeile entlang gesetzt werden soll
            if (start.row < end.row) {//prüft in welche Richtung das Schiff gesetzt werden soll
                for (int i = start.row; i <= end.row; i++) {
                    if (checkFieldsForShips(i, start, field)) {
                        //wenn die Bedingung wahr ist, dann gibt es einen Konflikt, also muss noConflict false zurückgeben
                        return false;
                    }
                }
                return true;
            } else {
                for (int i = start.row; i >= end.row; i--) {
                    if (checkFieldsForShips(i, start, field)) {
                        return false;
                    }
                }
                return true;
            }
        } else { //Schiff soll einer Spalte entlang platziert werden
            if (start.column < end.column) {
                for (int i = start.column; i <= end.column; i++) {
                    if (checkFieldsForShips(i, start, field)) {
                        return false;
                    }
                }
                return true;
            } else {
                for (int i = start.column; i >= end.column; i--) {
                    if (checkFieldsForShips(i, start, field)) {
                        return false;
                    }
                }
                return true;
            }
        }
    }*/

    //Prüft ob, an den anliegenden Feldern ein Schiff ist, unabhängig von dessen Status
    static boolean checkFieldsForShips(int i, final Coordinate start, final Field[][] field) {
        if (field[start.column + 1][i] == Field.SHIP || field[start.column + 1][i] == Field.SHIP_HIT) {
            return true;
        } else if (field[start.column - 1][i] == Field.SHIP || field[start.column - 1][i] == Field.SHIP_HIT) {
            return true;
        } else if (field[start.column][i + 1] == Field.SHIP || field[start.column][i + 1] == Field.SHIP_HIT) {
            return true;
        } else if (field[start.column][i - 1] == Field.SHIP || field[start.column][i - 1] == Field.SHIP_HIT) {
            return true;
        } else {
            return false;
        }
    }

    static Coordinate readCoordinate(final String prompt) {
        System.out.println(prompt);
        String input = "";
        try {
            input = Utility.readStringFromConsole();

        } catch (IOException e) {
            readCoordinate(prompt);
        }

        if (input.equals("exit")) {
            System.exit(0);
        } else if (isValidCoordinate(input)) {
            return toCoordinate(input);
        }
        return null;
    }

    static Coordinate getRandomUnshotCoordinate(final Field[][] field) {
        ArrayList<Coordinate> unshotWater = new ArrayList<Coordinate>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == Field.WATER) {
                    unshotWater.add(new Coordinate(i, j));
                }
            }
        }
        if (unshotWater.isEmpty()) {
            throw new IllegalStateException();
        }
        return unshotWater.get(Utility.getRandomInt(unshotWater.size()));

    }

    static Coordinate readEndCoordinate(final int length) {
        return readCoordinate(getEndCoordinatePrompt(length - 1));
    }

    static Coordinate readStartCoordinate(final int length) {
        return readCoordinate(getStartCoordinatePrompt(length));
    }

    static boolean allHit(final Field[][] field) {
        final int ALL_HIT = 14;
        return ALL_HIT == countHits(field);
    }

    static boolean endCondition(final Field[][] ownField, final Field[][] otherField) {
        return allHit(otherField) || allHit(ownField);
    }

    static boolean validPosition(final Coordinate start, final Coordinate end, final int length, final Field[][] field) {
        boolean eins = distance(start, end) == length;
        boolean zwei = onOneLine(start, end);
        boolean drei = noConflict(start, end, field);
        return eins && zwei && drei;
        //return onOneLine(start, end) && distance(start, end) == length -1 && noConflict(start, end, field);
    }

    static void shot(final Coordinate shot, final Field[][] field) {
        switch (field[shot.column][shot.row]) {
            case Field.WATER:
                field[shot.column][shot.row] = Field.WATER_HIT;
                break;
            case Field.SHIP:
                field[shot.column][shot.row] = Field.SHIP_HIT;
                if (shipSunk(shot, field)) {
                    fillWaterHits(shot, field);
                }
                break;
            default:
                break;
        }
    }

    static void turn(final Field[][] ownField, final Field[][] otherField) {
        showFields(ownField, otherField);
        Coordinate shot = readCoordinate("Geben Sie Ihren nächsten Schuss ein: ");
        shot(shot, otherField);
        shot(getRandomUnshotCoordinate(otherField), ownField);
    }

    static void outputWinner(final Field[][] ownField, final Field[][] otherField) {
        showFields(ownField, otherField);
        if (allHit(otherField)) {
            System.out.println("Du hast gewonnen!");
        } else if (allHit(ownField)) {
            System.out.println("Der Computer hat gewonnen!");
        }
    }

    static Field[][] initOtherField() {
        Field[][] field = new Field[size][size];
        setAllFree(field);
        for (int i = 2; i <= 5; ) {
            Coordinate start = getRandomCoordinate();
            Coordinate end = getRandomEndCoordinate(start, i - 1);
            if (validPosition(start, end, i - 1, field)) {
                placeShip(start, end, field);
                i++;
            }
        }
        return field;
    }

    static Field[][] initOwnField(final Field[][] otherField) {
        Field[][] field = new Field[size][size];
        setAllFree(field);
        showFields(field, otherField);
        for (int i = 5; i >= 2; ) {
            Coordinate start = readStartCoordinate(i);
            Coordinate end = readEndCoordinate(i + 1);
            if (validPosition(start, end, i - 1, field)) {
                placeShip(start, end, field);
                showFields(field, otherField);
                i--;
            } else {
                System.out.println("Ungültige Eingaben");

            }
        }
        return field;
    }

    static boolean noConflict(final Coordinate start, final Coordinate end, final Field[][] field) {
        for (
                int column = getMinSurroundingColumn(start, end);
                column <= getMaxSurroundingColumn(start, end);
                column++
        ) {
            for (
                    int row = getMinSurroundingRow(start, end);
                    row <= getMaxSurroundingRow(start, end);
                    row++
            ) {
                if (field[column][row] != Field.WATER) {
                    return false;
                }
            }
        }
        return true;
    }

    static void fillWaterHits(final Coordinate shot, final Field[][] field) {
        int columnMin = shot.column();
        while (columnMin > 0 && field[columnMin][shot.row()] == Field.SHIP_HIT) {
            columnMin--;
        }
        int columnMax = shot.column();
        while (columnMax < size - 1 && field[columnMax][shot.row()] == Field.SHIP_HIT) {
            columnMax++;
        }
        int rowMin = shot.row();
        while (rowMin > 0 && field[shot.column()][rowMin] == Field.SHIP_HIT) {
            rowMin--;
        }
        int rowMax = shot.row();
        while (rowMax < size - 1 && field[shot.column()][rowMax] == Field.SHIP_HIT) {
            rowMax++;
        }
        for (int column = columnMin; column <= columnMax; column++) {
            for (int row = rowMin; row <= rowMax; row++) {
                if (field[column][row] == Field.WATER) {
                    field[column][row] = Field.WATER_HIT;
                }
            }
        }
    }
}