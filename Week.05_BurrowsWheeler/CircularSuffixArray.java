package computerscience.algorithms.week10.burrows;

/**
 * @author Eduardo
 */
public class CircularSuffixArray {

    // cutoff to insertion sort
    private static final int CUTOFF = 15;
    private final String s;
    private final int[] index;
    private final int length;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException();
        }
        this.s = s;
        this.length = s.length();
        this.index = new int[this.length];

        for (int i = 0; i < this.length; i++) {
            index[i] = i;
        }

        // 3-way string quick sort suffixes
        sort(0, this.length - 1, 0);
    }

    // length of s
    public int length() {
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i) {
        if (i < 0 || i >= length) {
            throw new IllegalArgumentException();
        }
        return index[i];
    }

    // helper methods
    private void sort(int lo, int hi, int d) {

        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int v = charAt(lo, d);
        int i = lo + 1;
        while (i <= gt) {
            int t = charAt(i, d);
            if (t < v) {
                exch(lt++, i++);
            } else if (t > v) {
                exch(i, gt--);
            } else {
                i++;
            }
        }

        sort(lo, lt - 1, d);
        if (v >= 0) {
            sort(lt, gt, d + 1);
        }
        sort(gt + 1, hi, d);
    }

    private int charAt(int i, int d) {
        if (d == this.s.length()) {
            return -1;
        }
        return this.s.charAt((this.index[i] + d) % this.length);
    }

    // exchange index[i] and index[j]
    private void exch(int i, int j) {
        int temp = this.index(i);
        this.index[i] = this.index[j];
        this.index[j] = temp;
    }

    // insertion sort
    private void insertion(int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && less(j, j - 1, d); j--) {
                exch(j, j - 1);
            }
        }
    }

    // is i < j ?
    private boolean less(int i, int j, int d) {
        for (int m = 0; m < length; m++) {
            if (charAt(i, d + m) < charAt(j, d + m)) {
                return true;
            }
            if (charAt(i, d + m) > charAt(j, d + m)) {
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        System.out.println("Length: " + csa.length() + "\n");
        for (int i = 0; i < csa.length(); i++) {
            System.out.println(csa.index(i));
        }
    }
}
