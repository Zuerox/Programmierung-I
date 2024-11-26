

public class Rekursion {
    public record ListNode(
            int value,
            ListNode next
    ){}

   public record SinglyLinkedList(
           ListNode root
   ){}

    static SinglyLinkedList addFirst(final SinglyLinkedList list, final int value) {
        SinglyLinkedList newList = new SinglyLinkedList(new ListNode(value,list.root()));
        return newList;
    }

    static String toString(final SinglyLinkedList list) {
        if (list.root == null) {
            return "";
        }
        return list.root.value() + ", " + toString(new SinglyLinkedList(list.root.next()));
    }

   static SinglyLinkedList addLast(final SinglyLinkedList list, final int value) {
        if(list.root.next() == null){
            ListNode last = new ListNode(value, null);
            return new SinglyLinkedList(new ListNode(list.root.value(), last));
        }
        return new SinglyLinkedList(new ListNode(list.root.value(), addLast(new SinglyLinkedList(list.root.next), value).root));
   }

   static SinglyLinkedList remove(final SinglyLinkedList list, final int value) {
       if (list.root.next() != null) {
           if (list.root.value() == value) {
               return new SinglyLinkedList(list.root.next());
           }
           return new SinglyLinkedList(new ListNode(list.root.value(), remove(new SinglyLinkedList(list.root.next()), value).root));
       }
       return list;
   }


   public static void main(final String[] args) {
        ListNode knoten1 = new ListNode(1, null);
        SinglyLinkedList list1 = new SinglyLinkedList(knoten1);
        list1 = addLast(list1, 2);
        list1 = addLast(list1, 3);
        System.out.println(toString(list1));
   }
   //Aufgabe 2:

    public record TournamentNode(
            TournamentNode left,
            TournamentNode right,
            String winner,
            int points
    ){}

    static boolean finished(final TournamentNode root){
        return !root.winner.isEmpty();
    }

    static TournamentNode setPoints(final TournamentNode node, final int points){
        return new TournamentNode(node.left(), node.right(), node.winner, points);
    }

    static int powerOf2 (final int nonNegativeNumber){
        int ergebnis = 2;
        if (nonNegativeNumber == 0) {
            return 1;
        }
        for(int i = nonNegativeNumber; i > 1; i--){
            ergebnis*=2;
        }
        return ergebnis;
    }

    static int rowOffset(final int level, final int height) throws Exception {
        if(powerOf2(level) > powerOf2(height)){
            return powerOf2(level)/powerOf2(height);
        }
        else{
            throw new Exception("Ergebnis lässt sich nicht als Int darstellen");
        }
    }

}
