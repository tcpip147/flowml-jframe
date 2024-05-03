package com.tcpip147.ui.component;

import java.awt.*;

public abstract class Shape {

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void draw(Graphics2D g);
}
