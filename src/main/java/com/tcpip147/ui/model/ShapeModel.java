package com.tcpip147.ui.model;

import com.tcpip147.ui.component.Shape;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class ShapeModel {

    private List<Shape> shapeList;
    private ShapeReadService readService;
    private ShapeUpdateService updateService;

    public ShapeModel() {
        shapeList = new LinkedList<>();
        readService = new ShapeReadService(this);
        updateService = new ShapeUpdateService(this);
    }
}
