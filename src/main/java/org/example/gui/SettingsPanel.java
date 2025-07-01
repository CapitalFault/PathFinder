package org.example.gui;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.text.NumberFormat;

public class SettingsPanel extends JPanel {

    JLabel rowLabel;
    JLabel colLabel;
    JFormattedTextField rows;
    JFormattedTextField cols;
    JButton saveButton;
    JComboBox<String> setAlgorithm;


    public SettingsPanel() {
        rowLabel = new JLabel("Rows:");
        colLabel = new JLabel("Cols:");
        
        NumberFormat integerNumberInstance = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(integerNumberInstance);
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0);
        formatter.setMaximum(1000);

        rows = new JFormattedTextField(formatter);
        cols = new JFormattedTextField(formatter);
        rows.setColumns(10);
        cols.setColumns(10);

        rowLabel.setLabelFor(rows);
        colLabel.setLabelFor(cols);

        saveButton = new JButton("Save");

        String[] choices = {"Dijkstra", "A*"};
        setAlgorithm = new JComboBox<>(choices);

        add(rowLabel);
        add(rows);
        add(colLabel);
        add(cols);
        add(saveButton);
        add(setAlgorithm);
    }

    public JLabel getRowLabel() {
        return rowLabel;
    }

    public JLabel getColLabel() {
        return colLabel;
    }

    public JFormattedTextField getRows() {
        return rows;
    }

    public JFormattedTextField getCols() {
        return cols;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JComboBox<String> getSetAlgorithm() {
        return setAlgorithm;
    }

}
