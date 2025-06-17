package org.example.logic;

import org.example.model.Node;

import java.util.List;

public interface StepSolver {

    boolean isFinished();

    Node step();

    List<Node> reconstructPath();

}
