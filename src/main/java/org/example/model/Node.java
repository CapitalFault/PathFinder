package org.example.model;

public class Node {
    public int row, col;
    public boolean isWall = false;
    public boolean visited = false;
    public boolean isStart = false;
    public boolean isEnd = false;
    public double distance = Integer.MAX_VALUE;
    public Node previous = null;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node)) return false;
        Node n = (Node) o;
        return this.row == n.row && this.col == n.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }
}
