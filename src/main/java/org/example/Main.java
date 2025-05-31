package org.example;

import org.example.gui.MainFrame;
import org.example.model.Grid;
import org.example.model.Node;
import org.example.logic.DijkstraStepSolver;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(MainFrame::new);

        int rows = 10;
        int cols = 10;

        Grid grid = new Grid(rows, cols);

        /*
        // Beispiel: ein paar Wände setzen
        grid.getNode(3, 3).isWall = true;
        grid.getNode(3, 4).isWall = true;
        grid.getNode(3, 5).isWall = true;
        grid.getNode(3, 6).isWall = true;
        grid.getNode(4, 2).isWall = true;
        grid.getNode(5, 1).isWall = true;
        grid.getNode(6, 0).isWall = true;
        grid.getNode(2, 7).isWall = true;
        grid.getNode(1, 8).isWall = true;
        grid.getNode(0, 9).isWall = true;


        Node start = grid.getNode(0, 0);
        Node end = grid.getNode(9, 9);

        DijkstraStepSolver solver = new DijkstraStepSolver(grid, start, end);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Dijkstra-Algorithmus Schritt für Schritt ausführen. Drücke Enter...");
        while (!solver.isFinished()) {
            solver.step();
            printGrid(grid, start, end);
            scanner.nextLine(); // warten auf Eingabe
        }

        // Pfad ausgeben
        List<Node> path = solver.reconstructPath();
        System.out.println("Pfad gefunden:\n");
        printGridWithPath(grid, start, end, path); */
    }

    private static void printGrid(Grid grid, Node start, Node end) {
        Node[][] nodes = grid.getGrid();
        for (int r = 0; r < nodes.length; r++) {
            for (int c = 0; c < nodes[0].length; c++) {
                Node n = nodes[r][c];
                if (n.equals(start)) System.out.print("S ");
                else if (n.equals(end)) System.out.print("E ");
                else if (n.isWall) System.out.print("# ");
                else if (n.visited) System.out.print("o ");
                else System.out.print(". ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void printGridWithPath(Grid grid, Node start, Node end, List<Node> path) {
        Node[][] nodes = grid.getGrid();
        for (int r = 0; r < nodes.length; r++) {
            for (int c = 0; c < nodes[0].length; c++) {
                Node n = nodes[r][c];
                if (n.equals(start)) System.out.print("S ");
                else if (n.equals(end)) System.out.print("E ");
                else if (n.isWall) System.out.print("# ");
                else if (path.contains(n)) System.out.print("* ");
                else System.out.print(". ");
            }
            System.out.println();
        }
    }
}
