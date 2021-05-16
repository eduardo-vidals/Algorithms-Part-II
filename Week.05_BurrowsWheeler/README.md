# Burrows Wheeler
Solution to the Burrows Wheeler assignment.

## Summary
This revolutionary algorithm outcompresses gzip and PKZIP, is relatively easy to implement, and is not protected by any patents. It forms the basis of the Unix compression utility bzip2.

## The Problem
Implement the Burrows–Wheeler data compression algorithm. The Burrows–Wheeler data compression algorithm consists of three algorithmic components, which are applied in succession:

* Energy Calculation
  * The first step is to calculate the energy of each pixel, which is a measure of the importance of each pixel—the higher the energy, the less likely that the pixel will be included as part of a seam.

* Seam Identification
  * The next step is to find a vertical seam of minimum total energy. This is similar to the classic shortest path problem in an edge-weighted digraph. 

* Seam Removal
  * The final step is to remove from the image all of the pixels along the seam.

## Specification.
Programming assignment specification can be found [here.](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php)

