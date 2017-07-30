
/**
 * Created by kevin on 17-7-11.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private Boolean[][] Data;

    private int N = 0;
    private int numOpen = 0;
    private WeightedQuickUnionUF unionUnit;
    //private WeightedQuickUnionUF unionUnitForFull;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        N = n;
        int i, j;
        Data = new Boolean[n][n];

        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                Data[i][j] = false;
            }
        }


        unionUnit = new WeightedQuickUnionUF(n * n + 1);


    }

    // create n-by-n grid, with all sites blocked
    public void open(int row, int col) {

        row = row - 1;
        col = col - 1;
        if (row < 0 || row >= N || col < 0 || col >= N)
            throw new IllegalArgumentException();
        if (!Data[row][col]) {
            Data[row][col] = true;
            this.numOpen = this.numOpen + 1;
        }

        if (col - 1 >= 0)
            if (isOpen(row + 1, col - 1 + 1)) {
                unionUnit.union(row * N + col + 1, row * N + col);

            }

        if (col + 1 < N)
            if (isOpen(row + 1, col + 1 + 1)) {
                unionUnit.union(row * N + col + 1, row * N + col + 2);

            }
        if (row - 1 >= 0)
            if (isOpen(row - 1 + 1, col + 1)) {
                unionUnit.union(row * N + col + 1, (row - 1) * N + col + 1);

            }
        if (row + 1 < N)
            if (isOpen(row + 1 + 1, col + 1)) {
                unionUnit.union(row * N + col + 1, (row + 1) * N + col + 1);

            }

        if (row == 0) {
            unionUnit.union(0, row * N + col + 1);
        }


    }// open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {

        row = row - 1;
        col = col - 1;
        if (row < 0 || row >= N || col < 0 || col >= N)
            throw new IllegalArgumentException();

        return Data[row][col];
    }// is site (row, col) open?

    public boolean isFull(int row, int col) {
        row = row - 1;
        col = col - 1;
        if (row < 0 || row >= N || col < 0 || col >= N)
            throw new IllegalArgumentException();

        return unionUnit.connected(0, row * N + col + 1);

    }// is site (row, col) full?

    public int numberOfOpenSites() {
        return numOpen;
    }// number of open sites

    public boolean percolates() {

        int i;
        for (i = 0; i < N; i++) {
            if (unionUnit.connected(0, N * (N - 1) + i + 1))
            {
                return true;
            }
        }

        if (i == N)
            return false;
        else
            return false;
    }// does the system percolate?

    public static void main(String[] args) {

    }   // test client (optional)
}

