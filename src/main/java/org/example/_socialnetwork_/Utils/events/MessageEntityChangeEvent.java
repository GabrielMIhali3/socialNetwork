package org.example._socialnetwork_.Utils.events;

import org.example._socialnetwork_.domain.Message;

public class MessageEntityChangeEvent implements Event {
    private ChangeEventType type;
    private Message data, oldData;

    public MessageEntityChangeEvent(ChangeEventType type, Message data) {
        this.type = type;
        this.data = data;
    }

    public MessageEntityChangeEvent(ChangeEventType type, Message data, Message oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }
}
