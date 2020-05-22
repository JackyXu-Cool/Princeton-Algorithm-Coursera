import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] openSites;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }
        openSites = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
            }
            openSites[i] = (double)(p.numberOfOpenSites()) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(openSites) - 1.96 * StdStats.stddev(openSites) / Math.sqrt(openSites.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(openSites) + 1.96 * StdStats.stddev(openSites) / Math.sqrt(openSites.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(100, 100);
        System.out.println(stats.confidenceLo() + " - " + stats.confidenceHi());
    }
}
