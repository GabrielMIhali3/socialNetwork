package org.example._socialnetwork_.Utils.observer;


import org.example._socialnetwork_.Utils.events.Event;

public interface Observer<E extends Event> {
    void update(E e);
}