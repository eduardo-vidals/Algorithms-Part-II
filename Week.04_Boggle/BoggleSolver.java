package computerscience.algorithms.week9.boggle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashSet;

/**
 * @author Eduardo
 */
public class BoggleSolver {
    private Node root;
    private HashSet<String> words;

    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        for (String word : dictionary) {
            insert(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        words = new HashSet<>();
        for (int row = 0; row < board.rows(); row++) {
            for (int col = 0; col < board.cols(); col++) {
                getWords(board, root, "", row, col, 0, new boolean[board.rows()][board.cols()]);
            }
        }
        return words;
    }

    // get wors from the trie
    private void getWords(BoggleBoard b, Node n, String w, int r, int c, int i, boolean[][] m) {
        // validate to ensure a base case for DFS
        if (validate(b, r, c, m)) {
            char letter = b.getLetter(r, c);
            if (letter == 'Q') {
                w += "QU";
            } else {
                w += letter;
            }

            m[r][c] = true;

            Node x = get(n, w, i++);

            if (x == null) {
                m[r][c] = false;
                return;
            }

            if (letter == 'Q') {
                i++;
            }

            if (x.word && w.length() > 2) {
                words.add(w);
            }

            getWords(b, x.mid, w, r, c - 1, i, m);
            getWords(b, x.mid, w, r, c + 1, i, m);
            getWords(b, x.mid, w, r - 1, c, i, m);
            getWords(b, x.mid, w, r + 1, c, i, m);
            getWords(b, x.mid, w, r + 1, c + 1, i, m);
            getWords(b, x.mid, w, r - 1, c - 1, i, m);
            getWords(b, x.mid, w, r + 1, c - 1, i, m);
            getWords(b, x.mid, w, r - 1, c + 1, i, m);
            m[r][c] = false;
        }
    }

    // validate
    private boolean validate(BoggleBoard b, int r, int c, boolean[][] m) {
        return c < b.cols() && c >= 0 && r < b.rows() && r >= 0 && !m[r][c];
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (contains(word)) {
            if (word.length() < 3) {
                return 0;
            } else if (word.length() < 5) {
                return 1;
            } else if (word.length() < 6) {
                return 2;
            } else if (word.length() < 7) {
                return 3;
            } else if (word.length() < 8) {
                return 5;
            } else {
                return 11;
            }
        } else {
            return 0;
        }
    }

    // Node for our trie
    private static class Node {
        private boolean word;
        private final char c;
        private Node left, mid, right;

        Node(char c) {
            this.c = c;
        }
    }

    // insert into the trie
    private void insert(String key) {
        root = insert(root, key, 0);
    }

    // helper method for insert
    private Node insert(Node x, String key, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node(c);
        }
        if (c < x.c) {
            x.left = insert(x.left, key, d);
        } else if (c > x.c) {
            x.right = insert(x.right, key, d);
        } else if (d < key.length() - 1) {
            x.mid = insert(x.mid, key, d + 1);
        } else {
            x.word = true;
        }
        return x;
    }

    // contains method for trie
    private boolean contains(String key) {
        Node x = get(root, key, 0);
        if (x == null) {
            return false;
        } else {
            return x.word;
        }
    }

    // get method for trie
    private Node get(Node x, String key, int d) {
        while (x != null) {
            char c = key.charAt(d);

            if (c < x.c) {
                x = x.left;
            } else if (c > x.c) {
                x = x.right;
            } else if (d < key.length() - 1) {
                x = x.mid;
                d++;
            } else {
                return x;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

