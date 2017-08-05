import static java.lang.Math.abs;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    public int[][] board;
    private int hammingDis=0;
    private int manhattanDis=0;
    private int zeroI;
    private int zeroJ;
    public Board(int[][] blocks)
    {
        //board = new int[blocks.length][blocks.length];
        board=blocks.clone();
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board.length;j++)
            {
                if(board[i][j]==0)
                {
                    zeroI=i;
                    zeroJ=j;
                    continue;
                }
                if(board[i][j]!=i*board.length+j+1)
                {
                    hammingDis++;
                    manhattanDis+=abs(board[i][j]/board.length-i)+abs(board[i][j]%board.length-j-1);
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
        int num1=StdRandom.uniform(9);
        int num2=StdRandom.uniform(9);
        int i1=num1/board.length;
        int j1=num1%board.length-1;
        int i2=num2/board.length;
        int j2=num2%board.length-1;

        while(board[i1][j1]==0||board[i2][j2]==0)
        {
            if(board[i1][j1]==0)
            {
                 num1=StdRandom.uniform(9);
                 i1=num1/board.length;
                 j1=num1%board.length-1;
            }
            if(board[i2][j2]==0)
            {
                num2=StdRandom.uniform(9);
                i2=num2/board.length;
                j2=num2%board.length-1;
            }
        }
        int[][] newBoard=board.clone();
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



    }// all neighboring boards
    public String toString()
    {}// string representation of this board (in the output format specified below)

    public static void main(String[] args) // unit tests (not graded)
}