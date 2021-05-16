# Seam Carving
Solution to the Seam Carving assignment.

## Summary
Seam-carving is a content-aware image resizing technique where the image is reduced in size by one pixel of height (or width) at a time. A vertical seam in an image is a path of pixels connected from the top to the bottom with one pixel in each row; a horizontal seam is a path of pixels connected from the left to the right with one pixel in each column. Below left is the original 505-by-287 pixel image; below right is the result after removing 150 vertical seams, resulting in a 30% narrower image. Unlike standard content-agnostic resizing techniques (such as cropping and scaling), seam carving preserves the most interest features (aspect ratio, set of objects present, etc.) of the image.

## The Problem
In this assignment, you will create a data type that resizes a W-by-H image using the seam-carving technique.

## Energy Calculation
The first step is to calculate the energy of each pixel, which is a measure of the importance of each pixel—the higher the energy, the less likely that the pixel will be included as part of a seam.

## Seam Identification
The next step is to find a vertical seam of minimum total energy. This is similar to the classic shortest path problem in an edge-weighted digraph. 

## Seam Removal
The final step is to remove from the image all of the pixels along the seam.

## Specification.
Programming assignment specification can be found [here.](https://coursera.cs.princeton.edu/algs4/assignments/seam/specification.php)

