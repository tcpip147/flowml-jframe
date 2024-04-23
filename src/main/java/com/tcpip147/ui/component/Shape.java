package com.tcpip147.ui.component;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseEvent;

@Getter
@Setter
public abstract class Shape {

    private boolean selected;

    public abstract void draw(Graphics2D g);

    public abstract boolean isInBounded(MouseEvent e);
}
