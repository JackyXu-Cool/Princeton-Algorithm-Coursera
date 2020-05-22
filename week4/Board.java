import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Board {

    private int[][] tiles;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                this.tiles[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String result = "";
        result += tiles.length + "\n";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                result = result + tiles[i][j] + " ";
            }
            result += "\n";
        }
        return result;
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != j + i * tiles.length + 1) count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != 0) {
                    if (tiles[i][j] != j + i * tiles.length + 1) {
                        int xDifference = Math.abs((tiles[i][j] - 1) / tiles.length - i);
                        int yDifference = Math.abs((tiles[i][j] - 1) % tiles.length - j);
                        sum += xDifference + yDifference;
                    }
                }
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y)
            return true;
        if (y == null)
            return false;
        if (getClass() != y.getClass())
            return false;
        Board other = (Board) y;
        if (hashCode() != -1 && hashCode() == other.hashCode())
            return true;
        return Arrays.deepEquals(tiles, other.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int blankRow = -1;
        int blankCol = -1;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if(tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
        if (blankRow != 0) {
            int[][] newTiles = copyTiles(tiles);
            swapTop(newTiles, blankRow, blankCol);
            neighbors.push(new Board(newTiles));
        }
        if (blankRow != tiles.length - 1) {
            int[][] newTiles = copyTiles(tiles);
            swapBottom(newTiles, blankRow, blankCol);
            neighbors.push(new Board(newTiles));
        }
        if (blankCol != 0) {
            int[][] newTiles = copyTiles(tiles);
            swapLeft(newTiles, blankRow, blankCol);
            neighbors.push(new Board(newTiles));
        }
        if (blankCol != tiles.length - 1) {
            int[][] newTiles = copyTiles(tiles);
            swapRight(newTiles, blankRow, blankCol);
            neighbors.push(new Board(newTiles));
        }
        return neighbors;
    }

//    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twin = copyTiles(tiles);
        if(dimension() == 1) return null;
        if(tiles[0][0] != 0 && tiles[0][1] != 0) {
            int temp = twin[0][0];
            twin[0][0] = twin[0][1];
            twin[0][1] = temp;
        } else {
            int temp = twin[1][0];
            twin[1][0] = twin[1][1];
            twin[1][1] = temp;
        }
        return new Board(twin);
    }

    private int[][] copyTiles(int[][] tiles) {
        int[][] copy = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    private void swapBottom(int[][] board, int row, int col) {
        int temp = board[row][col];
        board[row][col] = board[row+1][col];
        board[row+1][col] = temp;
    }

    private void swapLeft(int[][] board, int row, int col) {
        int temp = board[row][col];
        board[row][col] = board[row][col-1];
        board[row][col-1] = temp;
    }

    private void swapRight(int[][] board, int row, int col) {
        int temp = board[row][col];
        board[row][col] = board[row][col+1];
        board[row][col+1] = temp;
    }

    private void swapTop(int[][] board, int row, int col) {
        int temp = board[row][col];
        board[row][col] = board[row-1][col];
        board[row-1][col] = temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = {{1,0},
                     {3,2}};
        System.out.println(new Board(a).twin().toString());
    }

}