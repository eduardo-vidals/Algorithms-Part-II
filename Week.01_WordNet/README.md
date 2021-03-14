# WordNet
This is my solution to the WordNet assignment.

## Summary
WordNet is a semantic lexicon for the English language that computational linguists and cognitive scientists use extensively. For example, WordNet was a key component in IBM’s Jeopardy-playing Watson computer system. WordNet groups words into sets of synonyms called synsets. For example, { AND circuit, AND gate } is a synset that represent a logical gate that fires only when all of its inputs fire. WordNet also describes semantic relationships between synsets. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, the synset { gate, logic gate } is a hypernym of { AND circuit, AND gate } because an AND gate is a kind of logic gate.

## The Problem
Your first task is to build the WordNet digraph: each vertex v is an integer that represents a synset, and each directed edge v→w represents that w is a hypernym of v. The WordNet digraph is a rooted DAG: it is acyclic and has one vertex—the root—that is an ancestor of every other vertex. However, it is not necessarily a tree because a synset can have more than one hypernym.

## Shortest Ancestral Path
An ancestral path between two vertices v and w in a digraph is a directed path from v to a common ancestor x, together with a directed path from w to the same ancestor x. A shortest ancestral path is an ancestral path of minimum total length. We refer to the common ancestor in a shortest ancestral path as a shortest common ancestor. Note also that an ancestral path is a path, but not a directed path.

## Measuring the Semantic Relatedness of Two Nouns
Semantic relatedness refers to the degree to which two concepts are related. 

## Outcast Detection
Outcast detection. Given a list of WordNet nouns x1, x2, ..., xn, which noun is the least related to the others?

## Specification.
Programming assignment specification can be found [here.](https://coursera.cs.princeton.edu/algs4/assignments/wordnet/specification.php)
