package com.tcpip147.ui.model;

import com.tcpip147.ui.component.Activity;
import com.tcpip147.ui.component.Shape;

import java.util.List;
import java.util.ListIterator;

public class ShapeUpdateService {

    private List<Shape> shapeList;

    public ShapeUpdateService(ShapeModel model) {
        this.shapeList = model.getShapeList();
    }

    public void createActivity(String name, int x, int y, int width) {
        Activity activity = new Activity(name, x, y, width);
        shapeList.add(activity);
    }

    public void putShapeOnTop(Shape shape) {
        ListIterator<Shape> iterator = shapeList.listIterator();
        Shape moveToEnd = null;
        while (iterator.hasNext()) {
            if (shape.equals(iterator.next())) {
                moveToEnd = shape;
                break;
            }
        }
        if (moveToEnd != null) {
            shapeList.remove(moveToEnd);
            shapeList.add(moveToEnd);
        }
    }
}
