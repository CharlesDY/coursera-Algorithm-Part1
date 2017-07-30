

/**
 * Created by kevin on 17-7-11.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    //private int[] T;
    //private int[] fractionNum;
    //private double[] percolationRate;
    private int Trials;
    private double mean;
    private double s;

    public PercolationStats(int n, int trials) {

        if(n<=0||trials<=0)
            throw new IllegalArgumentException();

        Percolation unionUnit;

        int[] fractionNum=new int[trials];
        int[] T=new int[trials];
        Trials=trials;
        double[] percolationRate = new double[trials];
        int i;
        for (i = 0; i < trials; i++) {
            unionUnit = new Percolation(n);
            while (!unionUnit.percolates()) {
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                unionUnit.open(row, col);
            }

            fractionNum[i] = unionUnit.numberOfOpenSites();
            T[i] = n * n;

        }

        for (i = 0; i < trials; i++) {
            percolationRate[i] = (double)fractionNum[i]/(double)T[i];
        }

        s=StdStats.stddev(percolationRate);
        mean=StdStats.mean(percolationRate);

    }// perform trials independent experiments on an n-by-n grid

    public double mean() {

        return mean;
    }// sample mean of percolation threshold

    public double stddev() {

        return s;
    }// sample standard deviation of percolation threshold

    public double confidenceLo() {
        double xBar = this.mean;
        double s = this.s;

        return xBar - 1.96 * s/(Math.sqrt((double)Trials));
    }// low  endpoint of 95% confidence interval

    public double confidenceHi() {
        double xBar = this.mean;
        double s = this.s;

        return xBar + 1.96 *s/(Math.sqrt((double)Trials));
    }// high endpoint of 95% confidence interval

    public static void main(String[] args){
        int n=Integer.parseInt(args[0]);
        int T=Integer.parseInt(args[1]);

        //int n=200;
        //int T=1000;

        PercolationStats percolate=new PercolationStats(n,T);
        System.out.println("mean                    = "+percolate.mean());
        System.out.println("stddev                  = "+percolate.stddev());
        System.out.println("95% confidence interval = ["+percolate.confidenceLo()+", "+percolate.confidenceHi()+"]");
    }// test client (described below)
}
