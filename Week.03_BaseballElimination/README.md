# Baseball Elimination
Solution to the Baseball Elimination assignment. 

## Summary
In the baseball elimination problem, there is a division consisting of n teams. At some point during the season, team i has w[i] wins, l[i] losses, r[i] remaining games, and g[i][j] games left to play against team j. A team is mathematically eliminated if it cannot possibly finish the season in (or tied for) first place. The goal is to determine exactly which teams are mathematically eliminated. For simplicity, we assume that no games end in a tie (as is the case in Major League Baseball) and that there are no rainouts (i.e., every scheduled game is played).

## The Problem
Given the standings in a sports division at some point during the season, determine which teams have been mathematically eliminated from winning their division.

# Max-Flow Formulation

## Trivial Elimination
If the maximum number of games team x can win is less than the number of wins of some other team i, then team x is trivially eliminated (as is Montreal in the example above). That is, if w\[x] + r\[x] < w\[i], then team x is mathematically eliminated.

## Non-trivial Elimination
Otherwise, we create a flow network and solve a maxflow problem in it. In the network, feasible integral flows correspond to outcomes of the remaining schedule. There are vertices corresponding to teams (other than team x) and to remaining divisional games (not involving team x). Intuitively, each unit of flow in the network corresponds to a remaining game. As it flows through the network from s to t, it passes from a game vertex, say between teams i and j, then through one of the team vertices i or j, classifying this game as being won by that team.

## Specification.
Programming assignment specification can be found [here.](https://coursera.cs.princeton.edu/algs4/assignments/baseball/specification.php)
