package org.example.model;

import java.util.*;

public class Grid {
    private final Node[][] grid;
    private final int rows;
    private final int cols;

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Node[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new Node(r, c);
    }

    public Node getNode(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) return null;
        return grid[row][col];
    }

    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int row = node.row, col = node.col;
        int[][] dirs = {
                {-1,0}, {1,0}, {0,-1}, {0,1}, // Normale schritte
                {-1,-1}, {-1,1}, {1,-1}, {1,1} // Diagonale schritte
        };
        for (int[] d : dirs) {
            int newRow = row + d[0], newCol = col + d[1];
            Node neighbor = getNode(newRow, newCol);
            if (neighbor == null) continue;

            if (Math.abs(d[0]) == 1 && Math.abs(d[1]) == 1) {
                Node neighbor1 = getNode(row + d[0], col); //vertical
                Node neighbor2 = getNode(row, col + d[1]); //horizontal
                if ((neighbor1 != null && neighbor1.isWall) && (neighbor2 != null && neighbor2.isWall)) {
                    continue;
                }
            }
            neighbors.add(neighbor);

            //Node neighbor = getNode(node.row + d[0], node.col + d[1]);
            //if (neighbor != null) neighbors.add(neighbor);
        }
        return neighbors;
    }

    public Node[][] getGrid() {
        return grid;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }
}
