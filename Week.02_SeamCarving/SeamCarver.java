package computerscience.algorithms.week7.seamcarving;

import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

/**
 * @author Eduardo
 */

public class SeamCarver {

    private Picture picture;
    private double[][] energy;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null){
            throw new IllegalArgumentException();
        }

        this.picture = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        Picture temp = picture;
        picture = new Picture(picture);
        return temp;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // update the energy array
    private void updateEnergy() {
        this.energy = new double[picture.width()][picture.height()];
        for (int i = 0; i < picture.width(); i++) {
            for (int j = 0; j < picture.height(); j++) {
                this.energy[i][j] = energy(i, j);
            }
        }
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()){
            throw new IllegalArgumentException();
        }

        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) {
            return 1000;
        }
        double gradientXY = gradientX(x, y) + gradientY(x, y);
        return Math.sqrt(gradientXY);
    }

    private int gradientX(int x, int y) {
        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);

        int leftRGB = left.getRGB();
        int rightRGB = right.getRGB();

        int rright = (leftRGB >> 16) & 0xFF;
        int gright = (leftRGB >>  8) & 0xFF;
        int bright = (leftRGB) & 0xFF;

        int rleft = (rightRGB >> 16) & 0xFF;
        int gleft = (rightRGB >>  8) & 0xFF;
        int bleft = (rightRGB) & 0xFF;

        int rx = rright - rleft;
        int gx = gright - gleft;
        int bx = bright - bleft;

        return (rx * rx) + (gx * gx) + (bx * bx);
    }

    private int gradientY(int x, int y) {
        Color top = picture.get(x, y - 1);
        Color bottom = picture.get(x, y + 1);

        int topRGB = top.getRGB();
        int bottomRGB = bottom.getRGB();

        int rtop = (topRGB >> 16) & 0xFF;
        int gtop = (topRGB >>  8) & 0xFF;
        int btop = (topRGB) & 0xFF;

        int rbottom = (bottomRGB >> 16) & 0xFF;
        int gbottom = (bottomRGB >>  8) & 0xFF;
        int bbottom = (bottomRGB) & 0xFF;

        int ry = rtop - rbottom;
        int gy = gtop - gbottom;
        int by = btop - bbottom;

        return (ry * ry) + (gy * gy) + (by * by);
    }

    private void transpose() {
        Picture temp = new Picture(picture.height(), picture.width());
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {
                temp.set(i, j, picture.get(j, i));
            }
        }
        picture = temp;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] seam = findVerticalSeam();
        transpose();
        return seam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        // update energy
        updateEnergy();

        // topological order is implied if read from left to right, top to bottom. (natural ordering)
        double[][] distTo = new double[width()][height()];
        int[][] edgeTo = new int[width()][height()];

        // set distTo for all vertices as infinity
        for (int x = 0; x < picture.width(); x++) {
            for (int y = 0; y < picture.height(); y++) {
                distTo[x][y] = Double.POSITIVE_INFINITY;
            }
        }

        // top row has our initial energy of 1000
        for (int x = 0; x < picture.width(); x++) {
            distTo[x][0] = this.energy[x][0];
        }

        // relax vertices from top to bottom, left to right
        for (int y = 0; y < picture.height(); y++) {
            for (int x = 0; x < picture.width(); x++) {

                // relax left
                if (y < picture.height() - 1 && x > 0) {
                    if (distTo[x - 1][y + 1] > distTo[x][y] + this.energy[x - 1][y + 1]) {
                        distTo[x - 1][y + 1] = distTo[x][y] + this.energy[x - 1][y + 1];
                        edgeTo[x - 1][y + 1] = x;
                    }
                }

                // relax bottom
                if (y < picture.height() - 1) {
                    if (distTo[x][y + 1] > distTo[x][y] + this.energy[x][y + 1]) {
                        distTo[x][y + 1] = distTo[x][y] + this.energy[x][y + 1];
                        edgeTo[x][y + 1] = x;
                    }
                }

                // relax right
                if (y < picture.height() - 1 && x < picture.width() - 1) {
                    if (distTo[x + 1][y + 1] > distTo[x][y] + this.energy[x + 1][y + 1]) {
                        distTo[x + 1][y + 1] = distTo[x][y] + this.energy[x + 1][y + 1];
                        edgeTo[x + 1][y + 1] = x;
                    }
                }
            }

        }

        // relax cost to reach sink
        // we will start from the very bottom as the distTo[] array has already calculated the distances
        // edgeSP is the edge that has the shortest path
        // energySP is the total energy at that path
        int edgeSP = -1;
        double energySP = Double.POSITIVE_INFINITY;

        for (int x = 0; x < picture.width(); x++) {
            if (energySP > distTo[x][picture.height() - 1]) {
                energySP = distTo[x][picture.height() - 1];
                edgeSP = x;
            }
        }

        // resulting array
        int[] seam = new int[height()];

        // this just checks whether there is a shortest path that can be calculated for the picture
        // if there isn't a shortest path, then return null
        if (energySP != Double.POSITIVE_INFINITY) {
            // x and y are our starting values
            int x = edgeSP;
            int y = picture.height() - 1;

            // loop until you reach the top edge
            // update seam array every time it increments down
            // go to the last edge of the shortest path by using edgeTo[x][y]
            // decrement y until you reach 0 (the top edge)
            while (y >= 0) {
                seam[y] = x;
                x = edgeTo[x][y];
                y--;
            }
            return seam;
        } else {
            return null;
        }
    }

    // remove horizontal seam from current picture
    public void removeVerticalSeam(int[] seam) {
        // if seam is null, then throw an exception
        if (seam == null) {
            throw new IllegalArgumentException();
        }

        // check that each x value is greater than or less than the width
        for (int x : seam) {
            if (x < 0 || x >= width()) {
                throw new IllegalArgumentException();
            }
        }

        // size of seam should be equal to height
        if (seam.length != height()) {
            throw new IllegalArgumentException();
        }

        // seam elements must be exactly one pixel away from each other
        // throw an exception if the digraph does not follow this requirement
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) >= 2) {
                throw new IllegalArgumentException();
            }
        }

        // height of the picture will be reduced by 1
        // after removal of horizontal seam, pixels in the seam
        // are not copied over into the new picture
        Picture temp = new Picture(width() - 1, height());
        int current = 0;
        for (int y = 0; y < height(); y++) {
            for (int x = 0; x < width(); x++) {
                if (x != seam[y]) {
                    temp.set(current, y, picture.get(x, y));
                    current++;
                }
            }
            // refresh after each row
            current = 0;
        }
        picture = temp;
    }

    // remove vertical seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        // if seam is null, then throw an exception
        if (seam == null) {
            throw new IllegalArgumentException();
        }

        // check that each y value is greater than or less than the height
        for (int val : seam) {
            if (val < 0 || val >= height()) {
                throw new IllegalArgumentException();
            }
        }
        // size of seam should be equal to height
        if (seam.length != width()) {
            throw new IllegalArgumentException();
        }

        // seam elements must be exactly one pixel away from each other
        // throw an exception if the digraph does not follow this requirement
        for (int i = 0; i < seam.length - 1; i++) {
            if (Math.abs(seam[i] - seam[i + 1]) >= 2) {
                throw new IllegalArgumentException();
            }
        }

        // height of the picture will be reduced by 1
        // after removal of horizontal seam, pixels in the seam
        // are not copied over into the new picture
        Picture temp = new Picture(width(), height() - 1);
        int current = 0;
        for (int y = 0; y < width(); y++) {
            for (int x = 0; x < height(); x++) {
                if (x != seam[y]) {
                    temp.set(y, current, picture.get(y, x));
                    current++;
                }
            }
            // refresh after each row
            current = 0;
        }
        picture = temp;
    }

    //  unit testing (optional)
    public static void main(String[] args) {

    }

}
