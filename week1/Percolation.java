import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF UF;
    private int length;
    private int top;
    private int bottom;
    private int[][] opened; // "0" closed, "1" opened
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal input");
        }
        top = 0;
        bottom = n * n + 1;
        openSites = 0;
        UF = new WeightedQuickUnionUF(n * n + 2);
        length = n;
        opened = new int[n][n];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > length || col > length) {
            throw new IllegalArgumentException("Illegal input");
        }
        if (isOpen(row, col)) return;

        opened[row - 1][col - 1] = 1;
        openSites++;

        int index = getIndex(row, col);

        if (row == 1) {
            UF.union(top, index);
        } else if (row == length) {
            UF.union(bottom, index);
        }

        if (row != 1 && isOpen(row - 1, col)) {
            UF.union(index, getIndex(row - 1, col));
        }
        if (row != length && isOpen(row + 1, col)) {
            UF.union(index, getIndex(row + 1, col));
        }
        if (col != 1 && isOpen(row, col - 1)) {
            UF.union(index, getIndex(row, col - 1));
        }
        if (col != length && isOpen(row, col + 1)) {
            UF.union(index, getIndex(row, col + 1));
        }
    }

    private int getIndex(int row, int col) {
        return (row - 1) * length + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > length || col > length) {
            throw new IllegalArgumentException("Illegal input");
        }
        return opened[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > length || col > length) {
            throw new IllegalArgumentException("Illegal input");
        }
        return UF.find(top) == UF.find(getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.find(top) == UF.find(bottom);
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(4);
        p.open(1,1);
        p.open(1,2);
        p.open(2,2);
        p.open(3,2);
        p.open(4,2);
        System.out.println(p.numberOfOpenSites());
        System.out.println(p.percolates());
    }
}