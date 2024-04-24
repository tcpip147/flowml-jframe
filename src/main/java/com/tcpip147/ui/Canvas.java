package com.tcpip147.ui;

import com.tcpip147.ui.component.Shape;
import com.tcpip147.ui.state.EventHandler;
import com.tcpip147.ui.state.State;
import com.tcpip147.ui.state.StateManager;
import com.tcpip147.ui.state.impl.DragReady;
import com.tcpip147.ui.state.impl.SelectReady;

import javax.swing.*;
import java.awt.*;


public class Canvas extends JPanel {

    private StateManager stateManager = new StateManager();
    private ShapeModel model = new ShapeModel();

    public Canvas() {
        setLayout(null);
        setBackground(new Color(60, 63, 65));

        addEventHandler(new SelectReady());
        addEventHandler(new DragReady());

        stateManager.setCurrentState(State.SELECT_READY);

        model.createActivity("A", 100, 100, 100);
        model.createActivity("B", 120, 120, 100);
    }

    private void addEventHandler(EventHandler eventHandler) {
        stateManager.addEventHandler(eventHandler);
        eventHandler.setStateManager(stateManager);
        eventHandler.setModel(model);
        eventHandler.setCanvas(this);
        addMouseListener(eventHandler.getMousePressedListener());
        addMouseListener(eventHandler.getMouseReleasedListener());
        addMouseMotionListener(eventHandler.getMouseDraggedListener());
        addMouseMotionListener(eventHandler.getMouseMovedListener());
    }

    @Override
    protected void paintComponent(Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;
        for (Shape shape : model.getShapeList()) {
            shape.draw(g);
        }
    }
}
