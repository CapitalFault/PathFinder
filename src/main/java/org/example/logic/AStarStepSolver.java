package org.example.logic;

import org.example.model.Grid;
import org.example.model.Node;

import java.util.*;

public class AStarStepSolver implements StepSolver {
    private final Grid grid;
    private final Node start;
    private final Node end;
    private final PriorityQueue<Node> queue;
    private final Set<Node> visited;
    private boolean finished = false;

    public AStarStepSolver(Grid grid, Node start, Node end) {
        this.grid = grid;
        this.start = start;
        this.end = end;

        queue = new PriorityQueue<>(Comparator.comparingDouble(n -> n.fScore));
        visited = new HashSet<>();

        start.distance = 0.0;
        start.fScore = heuristic(start, end);
        queue.add(start);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    // Führt einen einzelnen Schritt der Dijkstra-Suche aus
    @Override
    public Node step() {
        if (queue.isEmpty()) {
            finished = true;
            return null;
        }

        Node current = queue.poll();

        //System.out.println("F Score: " + current.fScore);
        //System.out.println("Distance: " + current.distance);
        //System.out.println("Heuristic: " + heuristic(current, end));
        //System.out.println(" ");

        if (visited.contains(current)) return null;

        visited.add(current);
        current.visited = true;

        if (current.equals(end)) {
            finished = true;
            return current;
        }

        for (Node neighbor : grid.getNeighbors(current)) {
            if (visited.contains(neighbor) || neighbor.isWall) continue;

            double moveCost = getMoveCost(current, neighbor);
            double newDist = current.distance + moveCost;
            if (newDist < neighbor.distance) {
                neighbor.distance = newDist;
                neighbor.fScore = newDist + heuristic(neighbor, end);
                neighbor.previous = current;
                queue.add(neighbor);
            }
        }

        return current; // für Animation: der zuletzt bearbeitete Knoten
    }

    // Kann z.B. nach isFinished() aufgerufen werden
    @Override
    public List<Node> reconstructPath() {
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null && current.previous != null) {
            path.add(current);
            current = current.previous;
        }
        if (current == start) {
            path.add(start);
            Collections.reverse(path);
            return path;
        } else {
            return Collections.emptyList(); // kein Pfad
        }
    }

    private double getMoveCost(Node a, Node b) {
        int distanceRow = Math.abs(a.row - b.row);
        int distanceCol = Math.abs(a.col - b.col);
        if(distanceRow == 1 && distanceCol == 1) {
            return Math.sqrt(2);
        } else {
            return 1.0;
        }
    }

    private double heuristic(Node a, Node b) {
        int dx = Math.abs(a.row - b.row);
        int dy = Math.abs(a.col - b.col);
        return Math.sqrt(dx * dx + dy * dy); // Euklidisch
    }
}

