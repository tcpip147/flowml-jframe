package com.tcpip147.ui.model;

import com.tcpip147.ui.component.Shape;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;

public class ShapeReadService {

    private List<Shape> shapeList;

    public ShapeReadService(ShapeModel model) {
        this.shapeList = model.getShapeList();
    }

    public Shape getTopShape(MouseEvent e) {
        ListIterator<Shape> iterator = shapeList.listIterator(shapeList.size());
        while (iterator.hasPrevious()) {
            Shape shape = iterator.previous();
            if (shape.isInBounded(e)) {
                return shape;
            }
        }
        return null;
    }
}
