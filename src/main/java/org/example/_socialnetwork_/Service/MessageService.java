package org.example._socialnetwork_.Service;

import org.example._socialnetwork_.Repository.Repository;
import org.example._socialnetwork_.Utils.events.ChangeEventType;
import org.example._socialnetwork_.Utils.events.MessageEntityChangeEvent;
import org.example._socialnetwork_.Utils.observer.Observable;
import org.example._socialnetwork_.Utils.observer.Observer;
import org.example._socialnetwork_.controller.MessageAlert;
import org.example._socialnetwork_.domain.Message;
import org.example._socialnetwork_.domain.Prietenie;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageService implements Observable<MessageEntityChangeEvent> {
    private Repository<Long, Message> repository;
    private List<Observer<MessageEntityChangeEvent>> observers = new ArrayList<>();

    public MessageService(Repository<Long, Message> repository) {
        this.repository = repository;
    }

    public Message addMessage(Message message) {
        if (repository.save(message).isEmpty()){
            MessageEntityChangeEvent event = new MessageEntityChangeEvent(ChangeEventType.ADD, message);
            notifyObservers(event);
            return null;
        }
        return message;
    }

    public Message deleteMessage(Long id) {
        Optional<Message> message = repository.delete(id);
        if (message.isPresent()){
            notifyObservers(new MessageEntityChangeEvent(ChangeEventType.DELETE, message.get()));
            return message.get();
        }
        return null;
    }

    public Iterable<Message> getAllMessages() {
        return repository.findAll();
    }

    public Message findMessage(Long id) {
        return repository.findOne(id).orElse(null);
    }

    @Override
    public void addObserver(Observer<MessageEntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<MessageEntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(MessageEntityChangeEvent t) {
        observers.forEach(observer -> observer.update(t));
    }
}
