package org.example.gui;

import org.example.logic.AStarStepSolver;
import org.example.logic.DijkstraStepSolver;
import org.example.logic.StepSolver;
import org.example.model.Grid;
import org.example.model.Node;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
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
    private SettingsPanel settingsPanel;


    public MainFrame() {
        setTitle("Path Finder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.grid = new Grid(25, 25);
        this.visualizerPanel = new VisualizerPanel(grid, this);
        this.buttonPanel = new ButtonPanel();
        this.settingsPanel = new SettingsPanel();

        if (grid.getCurrentStart() != null && grid.getCurrentEnd() != null){
            setCurrentAlgorithm();
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

        setLayout(new BorderLayout(2,2));

        add(visualizerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(settingsPanel, BorderLayout.NORTH);


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

        settingsPanel.getSetAlgorithm().addActionListener(e -> {
            String selectedAlgorithm = (String) settingsPanel.getSetAlgorithm().getSelectedItem();
            System.out.println(selectedAlgorithm);
            onGridOrEndpointChanged();
        });

        settingsPanel.getSaveButton().addActionListener(e -> {
            int row = 0;
            int col = 0;
            try {
                settingsPanel.getCols().commitEdit();
                settingsPanel.getRows().commitEdit();

                Object rows = settingsPanel.getRows().getValue();
                Object cols = settingsPanel.getCols().getValue();
                if (rows instanceof Integer && cols instanceof Integer) {
                    row = (Integer) rows;
                    col = (Integer) cols;
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Keine gültige Zahl eingegeben!");
                }
                System.out.println(row + "+" + col);
                onGridSizeChanged(row, col);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this,
                        "Fehler bei der eingabe!");

            }
        });
    }

    public void setCurrentAlgorithm() {
        if (settingsPanel.getSetAlgorithm().getSelectedItem().equals("A*")) {
            this.solver = new AStarStepSolver(grid, grid.getCurrentStart(),
                    grid.getCurrentEnd());
        } else if (settingsPanel.getSetAlgorithm().getSelectedItem().equals("Dijkstra")) {
            this.solver = new DijkstraStepSolver(grid, grid.getCurrentStart(),
                    grid.getCurrentEnd());
        } else {
            JOptionPane.showMessageDialog(this,
                    "Something went wrong with the algorithm selection!");
        }
    }

    public void onGridSizeChanged(int rows, int cols) {
        this.grid = new Grid(rows, cols);
        remove(visualizerPanel);
        this.visualizerPanel = new VisualizerPanel(this.grid, this);
        add(visualizerPanel, BorderLayout.CENTER);
        visualizerPanel.revalidate();
        onGridOrEndpointChanged();
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
            if (grid.getCurrentStart() != null && grid.getCurrentEnd() != null) {
                setCurrentAlgorithm();

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
        }
        this.visualizerPanel.repaint();
    }

    public StepSolver getSolver() {
        return this.solver;
    }

    public Grid getGrid() {
        return grid;
    }
}
