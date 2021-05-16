package computerscience.algorithms.week7.seamcarving;

import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author Eduardo
 */

public class PrintEnergy {

    public static void main(String[] args) {
        Picture picture = new Picture("C:\\Users\\Eduardo\\Documents\\IntelliJProjects\\IdeaProjects\\src\\main" +
                "\\resources\\seamcarving\\3x4.png");
        StdOut.printf("image is %d pixels wide by %d pixels high.\n", picture.width(), picture.height());
        SeamCarver sc = new SeamCarver(picture);

        StdOut.printf("Printing energy calculated for each pixel.\n");

        for (int row = 0; row < sc.height(); row++) {
            for (int col = 0; col < sc.width(); col++) {
                StdOut.printf("%9.0f ", sc.energy(col, row));
            }
            StdOut.println();
        }
    }

}
