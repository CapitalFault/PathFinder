package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    public ButtonPanel() {
        JButton startButton = new JButton("Start");
        add(startButton, BorderLayout.SOUTH);

        JButton autoSolve = new JButton("autoSolve");
        add(autoSolve);

        JButton setStart = new JButton("Set Start");
        add(setStart);

        JButton setEnd = new JButton("Set End");
        add(setEnd);
    }

}
