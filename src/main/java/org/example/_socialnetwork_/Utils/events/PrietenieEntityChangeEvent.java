package org.example._socialnetwork_.Utils.events;

import org.example._socialnetwork_.domain.Prietenie;

public class PrietenieEntityChangeEvent implements Event{
    private ChangeEventType type;
    private Prietenie data, oldData;

    public PrietenieEntityChangeEvent(ChangeEventType type, Prietenie data) {
        this.type = type;
        this.data = data;
    }

    public PrietenieEntityChangeEvent(ChangeEventType type, Prietenie data, Prietenie oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }
}
