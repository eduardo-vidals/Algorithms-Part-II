# Burrows Wheeler
Solution to the Burrows Wheeler assignment.

## Summary
This revolutionary algorithm outcompresses gzip and PKZIP, is relatively easy to implement, and is not protected by any patents. It forms the basis of the Unix compression utility bzip2.

## The Problem
Implement the Burrows–Wheeler data compression algorithm. The Burrows–Wheeler data compression algorithm consists of three algorithmic components, which are applied in succession:

* Burrows–Wheeler Transform
  * Given a typical English text file, transform it into a text file in which sequences of the same character occur near each other many times.

* Move-to-Front Encoding
  * Given a text file in which sequences of the same character occur near each other many times, convert it into a text file in which certain characters appear much more frequently than others.

* Huffman Compression
  * Given a text file in which certain characters appear much more frequently than others, compress it by encoding frequently occurring characters with short codewords and infrequently occurring characters with long codewords.

## Specification.
Programming assignment specification can be found [here.](https://coursera.cs.princeton.edu/algs4/assignments/burrows/specification.php)

