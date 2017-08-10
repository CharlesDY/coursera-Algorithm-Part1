import  java.lang.*;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.*;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.NoSuchElementException;

public class Board{
    private int[][] board;
    private int hammingDis=0;
    private int manhattanDis=0;
    private int zeroI;
    private int zeroJ;
    public Board(int[][] blocks)
    {
        board = new int[blocks.length][blocks.length];


        for(int i=0;i<blocks.length;i++)
        {
            for(int j=0;j<blocks.length;j++)
            {
                board[i][j]=blocks[i][j];
                if(blocks[i][j]==0)
                {
                    zeroI=i;
                    zeroJ=j;
                    continue;
                }
                else if(blocks[i][j]!=i*blocks.length+j+1)
                {
                    hammingDis++;
                    manhattanDis+=Math.abs((board[i][j]-1)/board.length-i)+Math.abs((board[i][j]-1)%board.length-j);
                }
            }
        }
    }// construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public int dimension()
    {
        return board.length;
    }// board dimension n
    public int hamming()
    {
        return hammingDis;
    }// number of blocks out of place
    public int manhattan()
    {
        return manhattanDis;
    }// sum of Manhattan distances between blocks and goal
    public boolean isGoal()
    {
        return hammingDis==0;
    }// is this board the goal board?
    public Board twin()
    {
        int N=board.length*board.length;
        int num1=1;
        int num2=N-1;

        int i1=(num1-1)/board.length;
        int j1=(num1-1)%board.length;
        int i2=(num2-1)/board.length;
        int j2=(num2-1)%board.length;

        if(board[i1][j1]==0||board[i2][j2]==0)
        {
            num1=2;
            num2=N-1;
            i1=(num1-1)/board.length;
            j1=(num1-1)%board.length;
            i2=(num2-1)/board.length;
            j2=(num2-1)%board.length;
        }


        /*while(board[i1][j1]==0||board[i2][j2]==0)
        {
            if(board[i1][j1]==0)
            {
                 num1=StdRandom.uniform(1,N);
                 i1=(num1-1)/board.length;
                 j1=(num1-1)%board.length;
            }
            if(board[i2][j2]==0)
            {
                num2=StdRandom.uniform(1,N);
                i2=(num2-1)/board.length;
                j2=(num2-1)%board.length;
            }
        }*/
        int[][] newBoard=new int[board.length][board.length];
        for(int i=0;i<board.length;i++)
            for(int j=0;j<board.length;j++)
                newBoard[i][j]=board[i][j];

        int temp=newBoard[i1][j1];
        newBoard[i1][j1]=newBoard[i2][j2];
        newBoard[i2][j2]=temp;
        return new Board(newBoard);
    }// a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that=(Board) y;
        if(this.dimension()!=that.dimension())
            return false;
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board.length;j++)
                if(this.board[i][j]!=that.board[i][j])
                    return false;
        }
        return true;

    }// does this board equal y?

    public Iterable<Board> neighbors()
    {
        Stack<Board> stack = new Stack<Board>();
        int col=zeroJ;
        int row=zeroI;
        int N=board.length;
        int[][] dataTmp=board.clone();
        if (col > 0) {
            exch(dataTmp, row, col, row, col-1);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row, col-1);
        }

        // shift with right
        if (col < N-1) {
            exch(dataTmp, row, col, row, col+1);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row, col+1);
        }

        // shift with up
        if (row > 0) {
            exch(dataTmp, row, col, row-1, col);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row-1, col);
        }

        // shift with down
        if (row < N-1) {
            exch(dataTmp, row, col, row+1, col);
            stack.push(new Board(dataTmp));
            exch(dataTmp, row, col, row+1, col);
        }
        return stack;
    }// all neighboring boards

    private void exch(int[][] matrix, int i, int j, int p, int q) {
        int tmp = matrix[i][j];
        matrix[i][j] = matrix[p][q];
        matrix[p][q] = tmp;
    }

    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(board.length + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }// string representation of this board (in the output format specified below)


    public static void main(String[] args)
    {
        /*In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        */
        int[][] test={{2,0},{1,3}};

        Board testB=new Board(test);
        StdOut.println(testB.twin());
        //StdOut.println(initial.hamming());
        //StdOut.println(initial.manhattan());
        //for(Board temp:initial.neighbors())
          //  StdOut.println(temp);
    }// unit tests (not graded)
}