package computerscience.algorithms.week8.baseball;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/**
 * @author Eduardo
 */
public class BaseballElimination {

    private final int numOfTeams;
    private final String[] teamNames;
    private final int[] wins;
    private final int[] losses;
    private final int[] remaining;
    private final int[][] gamesLeft;
    private final int numOfVertices;
    private final boolean[] isEliminated;
    private final ArrayList<Bag<String>> certificateOfElimination;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String fileName) {
        In in = new In(fileName);
        this.numOfTeams = Integer.parseInt(in.readLine());
        this.certificateOfElimination = new ArrayList<>();

        // temporary variables that helps calculate for gameVertices
        int teamVertices = numOfTeams - 1;

        // number of game vertices for the flow network
        int gameVertices = (teamVertices * (teamVertices - 1)) / 2;

        // total number of vertices for the flow entwork (+2 signifies the
        this.numOfVertices = gameVertices + numOfTeams + 2;

        // initialize arrays
        this.wins = new int[numOfTeams];
        this.losses = new int[numOfTeams];
        this.remaining = new int[numOfTeams];
        this.teamNames = new String[numOfTeams];
        this.isEliminated = new boolean[numOfTeams];
        this.gamesLeft = new int[numOfTeams][numOfTeams];

        // fullfill arrays
        for (int i = 0; i < numOfTeams; i++) {
            String line = in.readLine();
            if (line != null) {
                String[] items = line.trim().split("\\s++");
                int index = 0;
                teamNames[i] = items[index++];
                wins[i] = Integer.parseInt(items[index++]);
                losses[i] = Integer.parseInt(items[index++]);
                remaining[i] = Integer.parseInt(items[index++]);
                for (int j = 0; j < numOfTeams; j++) {
                    gamesLeft[i][j] = Integer.parseInt(items[j + index]);
                }
            }
            isEliminated[i] = false;
            this.certificateOfElimination.add(null);
        }

        // index of team that has max wins
        int maxWinIndex = calMaxWin();

        // calculate isEliminated and certificateOfElimination
        for (int x = 0; x < numOfTeams; x++) {
            // trivial elimination
            if (wins[x] + remaining[x] < wins[maxWinIndex]) {
                this.isEliminated[x] = true;
                Bag<String> bag = new Bag<>();
                bag.add(teamNames[maxWinIndex]);
                this.certificateOfElimination.set(x, bag);
            }
            // non trivial elimination
            else {
                FlowNetwork fn = constructFN(x);
                FordFulkerson alg = new FordFulkerson(fn, numOfVertices - 2, numOfVertices - 1);
                // capture incut, for certificateOfElimination
                for (int k = 0; k < numOfTeams; k++) {
                    // if (k == x) continue;
                    if (alg.inCut(k)) {
                        // team x could be eliminated
                        isEliminated[x] = true;
                        String team = teamNames[k];
                        if (certificateOfElimination.get(x) == null) {
                            Bag<String> bag = new Bag<>();
                            bag.add(team);
                            certificateOfElimination.set(x, bag);
                        } else {
                            certificateOfElimination.get(x).add(team);
                        }
                    }
                }
            }
        }

    }

    // number of teams
    public int numberOfTeams() {
        return numOfTeams;
    }

    // all teams
    public Iterable<String> teams() {
        Bag<String> teams = new Bag<>();
        for (String str : teamNames) {
            teams.add(str);
        }
        return teams;
    }

    // number of wins for given team
    public int wins(String team) {
        validateTeam(team);
        int index = indexOf(team);
        return wins[index];
    }

    // number of losses for given team
    public int losses(String team) {
        validateTeam(team);
        int index = indexOf(team);
        return losses[index];
    }

    // number of remaining games for given team
    public int remaining(String team) {
        validateTeam(team);
        int index = indexOf(team);
        return remaining[index];
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        validateTeam(team1);
        validateTeam(team2);
        int i = indexOf(team1);
        int j = indexOf(team2);
        return gamesLeft[i][j];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        validateTeam(team);
        return isEliminated[indexOf(team)];
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        validateTeam(team);
        return certificateOfElimination.get(indexOf(team));
    }

    // helper functions
    private int calMaxWin() {
        int max = 0;
        for (int i = 0; i < numOfTeams; i++) {
            if (wins[max] < wins[i]) {
                max = i;
            }
        }
        return max;
    }

    // construct flow network
    private FlowNetwork constructFN(int x) {
        FlowNetwork fn = new FlowNetwork(numOfVertices);
        // get team index
        int s = numOfVertices - 2; // source index
        int t = numOfVertices - 1; // destination index

        // add edges
        // 1. add edges from team vertices to t
        for (int i = 0; i < numOfTeams; i++) {
            // if the team is x, then do not include it in the flow network
            if (i == x) {
                continue;
            }
            double capacity = wins[x] + remaining[x] - wins[i];
            FlowEdge fe = new FlowEdge(i, t, capacity);
            fn.addEdge(fe);
        }

        // 2. add edges from game vertices to team vertices
        int gameIndex = numOfTeams;
        for (int i = 0; i < numOfTeams - 1; i++) {
            if (i == x) {
                continue;
            }
            for (int j = i + 1; j < numOfTeams; j++) {
                if (j == x) {
                    continue;
                }
                double capacity = Double.POSITIVE_INFINITY;

                FlowEdge ei = new FlowEdge(gameIndex, i, capacity);
                FlowEdge ej = new FlowEdge(gameIndex, j, capacity);
                fn.addEdge(ei);
                fn.addEdge(ej);

                // 3. add edges from source to game vertices
                FlowEdge eij = new FlowEdge(s, gameIndex, gamesLeft[i][j]);
                fn.addEdge(eij);
                gameIndex++;
            }
        }

        return fn;
    }

    // validation that a team name is in the file
    private void validateTeam(String team) {
        for (String str : teamNames) {
            if (str.equals(team)) {
                return;
            }
        }
        throw new IllegalArgumentException("invalid team name");
    }

    // returns index of corresponding teamName in array
    private int indexOf(String team) {
        for (int i = 0; i < numOfTeams; i++) {
            if (teamNames[i].equals(team)) {
                return i;
            }
        }
        return -1;
    }

    // unit tests
    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}