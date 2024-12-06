import java.util.Arrays;

public class Rekursion {
    public record ListNode(
            int value,
            ListNode next
    ) {
    }

    public record SinglyLinkedList(
            ListNode root
    ) {
    }

    static SinglyLinkedList addFirst(final SinglyLinkedList list, final int value) {
        return new SinglyLinkedList(new ListNode(value, list.root()));
    }

    static SinglyLinkedList addLast(final SinglyLinkedList list, final int value) {
        if (list.root.next() == null) {
            ListNode last = new ListNode(value, null);
            return new SinglyLinkedList(new ListNode(list.root.value(), last));
        }
        return new SinglyLinkedList(new ListNode(list.root.value(), addLast(new SinglyLinkedList(list.root.next), value).root));
    }

    static String toString(final SinglyLinkedList list) {
        if (list.root == null) {
            return "";
        }else if(list.root.next == null) {
            return list.root.value + "";
        }
        return list.root.value() + ", " + toString(new SinglyLinkedList(list.root.next()));
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
        int[] mergedArray = mergeSort(new int[]{44, 9, 2, 4, 4, -1, 12});
        System.out.println(Arrays.toString(mergedArray));
    }


    //Aufgabe 2:

    public record TournamentNode(
            TournamentNode left,
            TournamentNode right,
            String winner,
            int points
    ) {
    }

    static boolean finished(final TournamentNode root) {
        return !root.winner.isEmpty();
    }

    static TournamentNode setPoints(final TournamentNode node, final int points) {
        return new TournamentNode(node.left(), node.right(), node.winner, points);
    }

    static int powerOf2(final int nonNegativeNumber) {
        int ergebnis = 2;
        if (nonNegativeNumber == 0) {
            return 1;
        }
        for (int i = nonNegativeNumber; i > 1; i--) {
            ergebnis *= 2;
        }
        return ergebnis;
    }

    static int rowOffset(final int level, final int height) throws Exception {
        if (powerOf2(level) > powerOf2(height)) {
            return powerOf2(level) / powerOf2(height);
        } else {
            throw new Exception("Ergebnis lässt sich nicht als Int darstellen");
        }
    }

    //Übungsblatt 7

    public static int[] merge(final int[] a, final int[] b) {
        final int[] mergedArray = new int[a.length + b.length];

        for (int cntA = 0, cntB = 0; cntA + cntB < a.length + b.length; ) {
            final int cntMerged = cntA + cntB;

            if (cntA == a.length) {
                System.arraycopy(b, cntB, mergedArray, cntMerged, b.length - cntB);
                break;
            }

            if (cntB == b.length) {
                System.arraycopy(a, cntA, mergedArray, cntMerged, a.length - cntA);
                break;
            }

            if (a[cntA] < b[cntB]) {
                mergedArray[cntMerged] = a[cntA];
                cntA++;
            } else if (a[cntA] > b[cntB]) {
                mergedArray[cntMerged] = b[cntB];
                cntB++;
            } else {
                mergedArray[cntMerged] = a[cntA];
                cntA++;
            }
        }
        return mergedArray;


    }


    public static int[] mergeSort(final int[] array) {
        if (array.length <= 1) return array;

        final int middle = array.length / 2;
        final int[] left = mergeSort(Arrays.copyOfRange(array, 0, middle));
        final int[] right = mergeSort(Arrays.copyOfRange(array, middle, array.length));

        return merge(left, right);
    }
}

