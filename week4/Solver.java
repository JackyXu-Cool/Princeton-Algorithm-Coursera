import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private SearchNode goal;

    private class SearchNode implements Comparable<SearchNode> {             // A search node consists of the board, number of moves to reach
        private int moves;                 // this step and pointed to the previous search node
        private Board board;
        private SearchNode prev;

        public SearchNode(Board initial) {
            moves = 0;
            prev = null;
            board = initial;
        }
        public int compareTo(SearchNode other) {
            return board.manhattan() + moves - other.board.manhattan() - other.moves;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> nodeTree = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();
        SearchNode first = new SearchNode(initial);
        nodeTree.insert(first);
        twinPQ.insert(first);
        SearchNode deleted = nodeTree.delMin();
        SearchNode deletedTwin = twinPQ.delMin();

        while(!deleted.board.isGoal() && !deletedTwin.board.twin().isGoal()) {
            for (Board neighbor: deleted.board.neighbors()) {
                if (deleted.prev == null || !deleted.prev.board.equals(neighbor)) {
                    SearchNode newNode = new SearchNode(neighbor);
                    newNode.moves = deleted.moves + 1;
                    newNode.prev = deleted;
                    nodeTree.insert(newNode);
                }
            }
            for (Board neighbor: deletedTwin.board.neighbors()) {
                if (deletedTwin.prev == null || !deletedTwin.prev.board.equals(neighbor)) {
                    SearchNode newNode = new SearchNode(neighbor);
                    newNode.moves = deletedTwin.moves + 1;
                    newNode.prev = deletedTwin;
                    twinPQ.insert(newNode);
                }
            }
            deleted = nodeTree.delMin();
            deletedTwin = twinPQ.delMin();
        }
        if (deleted.board.isGoal()) this.goal = deleted;
        else this.goal = null;
    }

    public boolean isSolvable() {
        return goal != null;
    }

    public int moves() {
        if (this.goal == null) return -1;
        return goal.moves;
    }

    public Iterable<Board> solution() {
        if (goal == null) return null;
        Stack<Board> solution = new Stack<>();
        SearchNode current = goal;
        while (current != null) {
            solution.push(current.board);
            current = current.prev;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] board = {{2, 0},
                         {1, 3}};
        Solver solver = new Solver(new Board(board));
        Iterable<Board> a = solver.solution();
        System.out.println(solver.moves());
        for(Board b: a) {
            System.out.println(b.toString());
        }
    }
}
