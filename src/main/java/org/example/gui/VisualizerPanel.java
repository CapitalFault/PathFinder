package org.example.gui;

import org.example.model.Grid;
import org.example.model.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VisualizerPanel extends JPanel {
    private final Grid grid;
    private final int cellSize = 25;

    public VisualizerPanel(Grid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(grid.getRows() * cellSize, grid.getCols() * cellSize));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int col = e.getX() / cellSize;
                int row = e.getY() / cellSize;

                if (grid.isInBounds(row, col)) {
                    Node node = grid.getNode(row, col);
                    node.isWall = !node.isWall;
                    System.out.println("Klick auf Zelle: " + row + ", " + col);
                    repaint();
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
                else graphics.setColor(Color.WHITE);

                graphics.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                graphics.setColor(Color.GRAY);
                graphics.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
                    

            }
        }


    }
}
