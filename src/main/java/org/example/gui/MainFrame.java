package org.example.gui;

import org.example.model.Grid;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        Grid grid = new Grid(20, 20);
        VisualizerPanel visualizerPanel = new VisualizerPanel(grid);

        setLayout(new BorderLayout());
        add(visualizerPanel, BorderLayout.CENTER);

        ButtonPanel buttonPanel = new ButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);


        pack();
        setVisible(true);

        grid.getNode(5,5).isWall = true;
    }
}
