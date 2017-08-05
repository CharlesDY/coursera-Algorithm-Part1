import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;
import java.util.Comparator;

import java.lang.*;

public class Solver {
    

    private Node finalNode;
    private boolean solvable;
    private int moves=0;
    private int movesTwin=0;
    private class Node {
        private Board board;
        private Node prevNode;
        private int M_DIS;


        public Node(Board board, Node prevNode,int M_DIS) {
            this.board = board;
            this.prevNode = prevNode;
            this.M_DIS=M_DIS;
        }
    }
    
    public Solver(Board initial) {

        if(initial==null)
            throw new java.lang.IllegalArgumentException();

         MinPQ<Node> pq;
         MinPQ<Node> pqTwin;
        pq = new MinPQ<Node>(nodeComparator);
        pqTwin = new MinPQ<Node>(nodeComparator);

        solvable = false;

        Node node = new Node(initial, null,initial.manhattan());
        Node nodeTwin = new Node(initial.twin(), null,initial.manhattan());

        pq.insert(node);
        pqTwin.insert(nodeTwin);

        node = pq.delMin();
        nodeTwin = pqTwin.delMin();

        while (!node.board.isGoal() && !nodeTwin.board.isGoal()) {

            moves=node.M_DIS-node.board.manhattan()+1;
            movesTwin=nodeTwin.M_DIS-nodeTwin.board.manhattan()+1;
            for (Board b : node.board.neighbors()) {
                if (node.prevNode == null || !b.equals(node.prevNode.board)) {
                    Node neighbor = new Node(b, node,b.manhattan()+moves);
                    pq.insert(neighbor);
                }
            }

            for (Board bTwin : nodeTwin.board.neighbors()) {
                if (nodeTwin.prevNode == null || !bTwin.equals(nodeTwin.prevNode.board)) {
                    Node neighbor = new Node(bTwin, nodeTwin,bTwin.manhattan()+movesTwin);
                    pqTwin.insert(neighbor);
                }
            }

            node = pq.delMin();
            nodeTwin = pqTwin.delMin();
        }

        if (node.board.isGoal()) {
            solvable = true;
            finalNode = node;
        }


    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if(this.isSolvable())
            return finalNode.M_DIS-finalNode.board.manhattan();
        else
            return -1;
    }
    

    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!solvable) return null;

        Node current = finalNode;
        Stack<Board> boards = new Stack<Board>();
        boards.push(current.board);

        while (current.prevNode != null) {
            boards.push(current.prevNode.board);
            current = current.prevNode;
        }
        return boards;
    }



    private Comparator<Node> nodeComparator = new Comparator<Node>() {   
        @Override
        public int compare(Node a, Node b) {
            return a.M_DIS - b.M_DIS;
        }
    };
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());

            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}