package com.tcpip147.ui.component;

import lombok.Getter;

@Getter
public class LinkingPortPoint {

    private String dir;
    private int x;

    public LinkingPortPoint(String dir, int x) {
        this.dir = dir;
        this.x = x;
    }
}
