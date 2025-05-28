package org.example.model;

import java.util.*;

public class Grid {
    private final Node[][] grid;
    private final int rows, cols;

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
        int[][] dirs = {
                {-1,0}, {1,0}, {0,-1}, {0,1}, // Normale schritte
                {-1,-1}, {-1,1}, {1,-1}, {1,1} // Diagonale schritte
        };
        for (int[] d : dirs) {
            Node neighbor = getNode(node.row + d[0], node.col + d[1]);
            if (neighbor != null) neighbors.add(neighbor);
        }
        return neighbors;
    }

    public Node[][] getGrid() {
        return grid;
    }
}
