package com.tcpip147.ui.state;

import java.util.ArrayList;
import java.util.List;

public class StateManager {

    private List<EventHandler> eventHandlerList = new ArrayList<>();

    public void addEventHandler(EventHandler eventHandler) {
        this.eventHandlerList.add(eventHandler);
    }

    public void setCurrentState(State currentState) {
        for (EventHandler eventHandler : eventHandlerList) {
            eventHandler.setCurrentState(currentState);
        }
    }
}
