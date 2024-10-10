public class Sum {
    public static void main(String[] args) {
        System.out.println(
                Integer.parseInt(args[0]) + Integer.parseInt(args[1])
        );
    }

    static int product(int x, int y) {
        return x * y;
    }

    static int squaresum(int x, int y) {
        return x * x + y * y;
    }

    static void output(String content){
        System.out.println(content);
    }

    static void warning(){
        System.out.println("WARNUNG");
    }
}
