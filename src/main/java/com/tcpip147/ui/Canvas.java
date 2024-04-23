package com.tcpip147.ui;

import com.tcpip147.ui.component.Shape;
import com.tcpip147.ui.controller.MouseLeftDownController;
import com.tcpip147.ui.model.ShapeModel;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private ActionManager actionManager = new ActionManager();
    private ShapeModel model = new ShapeModel();

    public Canvas() {
        setLayout(null);
        setBackground(new Color(60, 63, 65));

        model.getUpdateService().createActivity("A", 100, 100, 100);
        model.getUpdateService().createActivity("B", 150, 110, 100);
        addMouseListener(new MouseLeftDownController(this, actionManager, model));
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
