package org.example.gui;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {



    JButton startButton;
    JButton step;
    JButton setStart;
    JButton setEnd;

    public ButtonPanel() {

        startButton = new JButton("Start");
        step = new JButton("Step");
        setStart = new JButton("Set Start");
        setEnd = new JButton("Set End");

        add(startButton, BorderLayout.SOUTH);
        add(step);
        add(setStart);
        add(setEnd);

    }

    public JButton getStartButton() {
        return startButton;
    }

    public JButton getStep() {
        return step;
    }

    public JButton getSetStart() {
        return setStart;
    }

    public JButton getSetEnd() {
        return setEnd;
    }

}
