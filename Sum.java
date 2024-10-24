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

    static String grade(final int points){
        if(points > 100 || points < 0){
            return "Ungültige Punktzahl";
        }else if(points < 50){
            return "5,0";
        }else if(points < 59){
            return "4,0";
        }else if(points < 67){
            return "3,7";
        }else if(points < 72){
            return "3,3";
        }else if(points < 77){
            return "3,0";
        }else if(points < 81){
            return "2,7";
        }else if(points < 85){
            return "2,3";
        }else if(points < 89){
            return "2,0";
        }else if(points < 92){
            return "1,7";
        }else if(points < 97){
            return "1,3";
        }else{
            return "1,0";
        }
    }
}
