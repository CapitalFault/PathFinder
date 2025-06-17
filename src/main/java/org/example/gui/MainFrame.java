package org.example.gui;

import org.example.logic.AStarStepSolver;
import org.example.logic.DijkstraStepSolver;
import org.example.logic.StepSolver;
import org.example.model.Grid;
import org.example.model.Node;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class MainFrame extends JFrame {
    public static boolean setStart = false;
    public static boolean setEnd = false;

    public List<Node> path;

    private Grid grid;
    private StepSolver solver;
    private VisualizerPanel visualizerPanel;
    private ButtonPanel buttonPanel;


    public MainFrame() {
        setTitle("Path Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.grid = new Grid(25, 25);
        this.visualizerPanel = new VisualizerPanel(grid, this);
        this.buttonPanel = new ButtonPanel();

        if (grid.getCurrentStart() != null && grid.getCurrentEnd() != null){
            this.solver = new AStarStepSolver(grid, grid.getCurrentStart(),
                    grid.getCurrentEnd());
        } else {
            System.err.println("Start- und Endknoten nicht gesetzt!");
            buttonPanel.getStartButton().setEnabled(false);
            buttonPanel.getStep().setEnabled(false);
            buttonPanel.getSetEnd().setEnabled(false);
            buttonPanel.getSetStart().setEnabled(false);

            JOptionPane.showMessageDialog(this,
                    "Start- und Endknoten nicht gesetzt!","1. Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }

        setLayout(new BorderLayout());

        add(visualizerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        buttonPanel.getStartButton().addActionListener(e -> {
            new Thread(() -> {
                while (!solver.isFinished()) {
                    solver.step();
                    SwingUtilities.invokeLater(() -> visualizerPanel.repaint());
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        ex.printStackTrace();
                        break;
                    }
                }

                path = solver.reconstructPath();
                visualizerPanel.repaint();
                JOptionPane.showMessageDialog(this,
                        "Distanz zwischen Start und Ende ist " + grid.getCurrentEnd().distance);
            }).start();
        });

        buttonPanel.getStep().addActionListener(e -> {
            new Thread(() -> {
                if (!solver.isFinished()) {
                    solver.step();
                    visualizerPanel.repaint();
                } else {
                    path = solver.reconstructPath();
                    visualizerPanel.repaint();
                    JOptionPane.showMessageDialog(this,
                            "Distanz zwischen Start und Ende ist " + grid.getCurrentEnd().distance);
                }
            }).start();
        });

        buttonPanel.getSetStart().addActionListener(e -> {
            setStart = true;
            setEnd = false;

            System.out.println("SetStart");
        });

        buttonPanel.getSetEnd().addActionListener(e -> {
            setEnd = true;
            setStart = false;

            System.out.println("SetEnd");
        });

        buttonPanel.getReset().addActionListener(e -> {
            onGridOrEndpointChanged();
        });


    }

    public void onGridOrEndpointChanged() {
        this.path = null;
        for (int row = 0; row < grid.getRows(); row++) {
            for (int col = 0; col < grid.getCols(); col++) {
                Node node = grid.getNode(row, col);
                node.visited = false;
                node.distance = Integer.MAX_VALUE;
                node.previous = null;
            }
        }

        Node currentStart = grid.getCurrentStart();
        Node currentEnd = grid.getCurrentEnd();

        if (currentStart != null && currentEnd != null) {
            this.solver = new AStarStepSolver(grid, currentStart, currentEnd);
            System.out.println("Solver aktualisiert!");

            if (buttonPanel != null) {
                buttonPanel.getStartButton().setEnabled(true);
                buttonPanel.getStep().setEnabled(true);

            }
        } else {
            System.err.println("Start- und Endknoten nicht gesetzt!");
            this.solver = null;
            if (buttonPanel != null) {
                buttonPanel.getStartButton().setEnabled(false);
                buttonPanel.getStep().setEnabled(false);
            }
            JOptionPane.showMessageDialog(this,
                    "Start- und Endknoten nicht gesetzt!");
        }

        this.visualizerPanel.repaint();
    }

    public StepSolver getSolver() {
        return this.solver;
    }
}
