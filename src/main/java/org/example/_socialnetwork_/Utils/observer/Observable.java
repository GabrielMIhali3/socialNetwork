package org.example._socialnetwork_.Utils.observer;


import org.example._socialnetwork_.Utils.events.Event;

public interface Observable<E extends Event> {
    void addObserver(Observer<E> e);
    void removeObserver(Observer<E> e);
    void notifyObservers(E t);
}
