package com.tcpip147;

import com.tcpip147.ui.Canvas;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("flow");
        frame.add(new Canvas());
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}