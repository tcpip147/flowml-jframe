package com.tcpip147.ui;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActionManager {

    private ActionState state;

    public ActionManager() {
        state = ActionState.SELECT_READY;
    }
}
