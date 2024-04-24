package com.tcpip147.ui;

import com.tcpip147.ui.component.Activity;
import com.tcpip147.ui.component.Shape;
import lombok.Getter;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

@Getter
public class ShapeModel {

    private List<Shape> shapeList = new LinkedList<>();

    public List<Shape> getDrawableShapeList() {
        return shapeList.stream().filter(shape -> shape.isVisible()).toList();
    }

    public Shape getTopShape(MouseEvent e) {
        List<Shape> shapeList = getDrawableShapeList();
        ListIterator<Shape> iterator = shapeList.listIterator(shapeList.size());
        while (iterator.hasPrevious()) {
            Shape shape = iterator.previous();
            if (shape.isInBounded(e)) {
                return shape;
            }
        }
        return null;
    }

    public void createActivity(String name, int x, int y, int width) {
        Activity activity = new Activity(name, x, y, width);
        shapeList.add(activity);
    }

    public void putShapeOnTop(Shape shape) {
        ListIterator<Shape> iterator = getDrawableShapeList().listIterator();
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

    public void selectShape(Shape shape) {
        for (Shape other : getDrawableShapeList()) {
            if (other.equals(shape)) {
                other.setSelected(true);
            } else {
                other.setSelected(false);
            }
        }
        putShapeOnTop(shape);
    }
}
