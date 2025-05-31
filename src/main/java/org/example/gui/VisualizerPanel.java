package org.example.gui;

import org.example.gui.MainFrame;
import org.example.model.Grid;
import org.example.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VisualizerPanel extends JPanel {
    private final Grid grid;
    private final int cellSize = 25;
    private MainFrame mainFrame;

    public VisualizerPanel(Grid grid, MainFrame mainFrame) {
        this.grid = grid;
        setPreferredSize(new Dimension(grid.getCols() * cellSize, grid.getRows() * cellSize));
        this.mainFrame = mainFrame;


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int clickedCol = e.getX() / cellSize;
                int clickedRow = e.getY() / cellSize;

                if (grid.isInBounds(clickedRow, clickedCol)) {
                    Node clickedNode = grid.getNode(clickedRow, clickedCol);
                    boolean configChanged = false;

                    if (MainFrame.setStart) {
                        if (clickedNode.isEnd) {
                            System.out.println("start kann nicht auf ende gesetzt werden!");
                        } else if (grid.getCurrentStart() != clickedNode) {
                            if (grid.getCurrentStart() != null) {
                                grid.getCurrentStart().isStart = false;
                            }
                            clickedNode.isStart = true;
                            grid.setCurrentStart(clickedNode);
                            configChanged = true;
                        }

                    } else if (MainFrame.setEnd) {
                        if (clickedNode.isStart) {
                            System.out.println("end kann nicht auf start gesetzt werden!");
                        } else if (grid.getCurrentEnd() != clickedNode) {
                            if (grid.getCurrentEnd() != null) {
                                grid.getCurrentEnd().isEnd = false;
                            }
                            clickedNode.isEnd = true;
                            grid.setCurrentEnd(clickedNode);
                            configChanged = true;
                        }

                    } else if (!MainFrame.setStart && !MainFrame.setEnd) {
                        if (!clickedNode.isStart && !clickedNode.isEnd) {
                            clickedNode.isWall = !clickedNode.isWall;
                            configChanged = true;
                            System.out.println("Wand umgeschaltet bei: " + clickedRow +", " + clickedCol);
                        } else {
                            System.out.println("wand kann nicht auf start oder ende gesetzt werden!");
                        }
                    }
                    if (configChanged) {
                        mainFrame.onGridOrEndpointChanged();
                    }
                    MainFrame.setStart = false;
                    MainFrame.setEnd = false;
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        for(int row = 0; row < grid.getRows(); row++){
            for (int col = 0; col < grid.getCols(); col++){
                Node node = grid.getNode(row, col);
                
                if (node.isWall) graphics.setColor(Color.BLACK);
                else if (node.visited) graphics.setColor(Color.YELLOW);
                else if (node.isStart) graphics.setColor(Color.GREEN);
                else if (node.isEnd) graphics.setColor(Color.RED);
                else graphics.setColor(Color.WHITE);

                graphics.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                graphics.setColor(Color.GRAY);
                graphics.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
                    

            }
        }


    }
}
